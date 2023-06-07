package com.example.stoks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.stocks.R
import com.example.stocks.databinding.DetailItemLayoutBinding
import kotlin.math.abs

class DetailedItemFragment : Fragment() {

    private var binding: DetailItemLayoutBinding by autoCleared()
    private val viewModel: ItemViewModel by activityViewModels()

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
            binding.itemName.text = it.stockName
            binding.itemPrice.text = "$%.2f".format(it.currPrice)
            binding.itemAmount.text = it.stockAmount.toString()
            binding.itemAmountTotal.text =
                "$%.2f".format(it.currPrice * it.stockAmount)
            Glide.with(requireContext()).load(it.stockImage).circleCrop()
                .into(binding.itemImage)

            val profit = (it.currPrice - it.stockPrice) * it.stockAmount
            if (profit >= 0 ) {
                binding.itemProfit.text =  "$%.2f".format(profit)
                binding.itemProfitTitle.text = getString(R.string.profit)
             } else {
                binding.itemProfitTitle.text = getString(R.string.loss)
                binding.itemProfit.text =  "-$%.2f".format(abs(profit))
             }
        }

        super.onViewCreated(view, savedInstanceState)
    }



}