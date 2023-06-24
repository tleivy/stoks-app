package com.example.stoks.ui.detailitem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.stoks.R
import com.example.stoks.data.local.ItemDao
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.utils.Constants
import com.example.stoks.data.utils.Success
import com.example.stoks.databinding.DetailItemLayoutBinding
import com.example.stoks.ui.ItemViewModel
import com.example.stoks.data.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.abs
@AndroidEntryPoint
class DetailedItemFragment : Fragment() {

    private var binding: DetailItemLayoutBinding by autoCleared()
    private val viewModel: ItemViewModel by activityViewModels()

    @Inject
    lateinit var stockRemoteDataSource: StockRemoteDataSource

    @Inject
    lateinit var itemDao: ItemDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailItemLayoutBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.chosenItem.observe(viewLifecycleOwner) {
            // Initialize prices from local database
            binding.itemPrice.text = "$%.2f".format(it.currPrice)
            binding.itemAmountTotal.text =
                "$%.2f".format(it.currPrice * it.stockAmount)
            binding.itemProfit.text = "$0.00"

            val stockSymbol = it.stockSymbol
            val token = Constants.API_KEY
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = stockRemoteDataSource.getQuote(stockSymbol, token)
                    if (response.status is Success) {
                        val currPrice = response.status.data?.c
                        val openingPrice = response.status.data?.o
                        Log.d("PRICES", "currPrice, $stockSymbol: $$currPrice")
                        Log.d("PRICES", "openingPrice, $stockSymbol: $$openingPrice")

                        withContext(Dispatchers.Main) {
                            if (currPrice != null) {
                                it.currPrice = currPrice
                                binding.itemPrice.text = "$%.2f".format(currPrice) // update UI immediately
                                binding.itemAmountTotal.text =
                                    "$%.2f".format(it.currPrice * it.stockAmount)
                                val profit = (it.currPrice - it.stockPrice) * it.stockAmount
                                if (profit >= 0) {
                                    binding.itemProfit.text = "$%.2f".format(profit)
                                    binding.itemProfitTitle.text = getString(R.string.profit)
                                } else {
                                    binding.itemProfitTitle.text = getString(R.string.loss)
                                    binding.itemProfit.text = "-$%.2f".format(abs(profit))
                                }
                                viewModel.updateItem(it)
                            }
                        }
                    } else if (response.status is Error) {
                        val errorMessage = response.status.message
                        it.currPrice = 0.0
                        binding.itemPrice.text = "0.0" // update UI immediately
                        binding.itemAmountTotal.text =
                            R.string.networkError.toString()
                        val profit = 0.0
                        binding.itemProfit.text = R.string.networkError.toString()
                        binding.itemProfitTitle.text = getString(R.string.profit)
                        viewModel.updateItem(it)
                    }
                } catch (e: Exception) {
                    // Handle exception
                }
            }

            binding.itemName.text = it.stockName
            binding.itemAmount.text = it.stockAmount.toString()
            Glide.with(requireContext()).load(it.stockImage).circleCrop()
                .into(binding.itemImage)

        }

        super.onViewCreated(view, savedInstanceState)
    }
}