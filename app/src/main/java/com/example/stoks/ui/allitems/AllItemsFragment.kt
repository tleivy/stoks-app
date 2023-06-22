package com.example.stoks.ui.allitems

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.R
import com.example.stoks.databinding.AllItemsFragmentBinding
import com.example.stoks.ui.ItemViewModel
import com.example.stoks.data.local.StocksDataMaps
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.data.remote_db.StockService
import com.example.stoks.data.utils.Constants
import com.example.stoks.data.utils.Success
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random
@AndroidEntryPoint
class AllItemsFragment : Fragment(){

    private var _binding:AllItemsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllItemsFragmentBinding.inflate(inflater,container,false)

        binding.addItemButton.setOnClickListener{
            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println(1)
        super.onViewCreated(view, savedInstanceState)
        println(2)
        viewModel.items?.observe(viewLifecycleOwner) {
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            val stockService = retrofit.create(StockService::class.java)
            val stockRemoteDataSource = StockRemoteDataSource(stockService)


            val itemsList = viewModel.items?.value
            itemsList?.forEach { item ->
                val symbol = item.stockName
                println(symbol)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val response = stockRemoteDataSource.getQuote(symbol)
                        if (response.status is Success) {
                            val stockData = response.status.data
                            println(stockData)
                            val currPrice = stockData?.c
                            withContext(Dispatchers.Main) {
                                if (currPrice != null) {
                                    item.currPrice = currPrice
                                }
                            }
                        } else if (response.status is Error) {
                            val errorMessage = response.status.message
                            // Handle error response with the provided error message
                        }
                    } catch (e: Exception) {
                        // Handle exception
                    }
                }
            }

















//            val itemsList = viewModel.items?.value
//            itemsList?.forEach {item ->
//                val pricesArr = StocksDataMaps.stockPrices[item.stockName]
//                if (pricesArr != null) {
//                    val randomInt = Random.nextInt(3)  // A random int in 0-2
//                    val currPrice = pricesArr[randomInt] ?: 0.0
//                    item.currPrice = currPrice
//                }
//            }

            binding.recycler.adapter = ItemAdapter(it, object : ItemAdapter.ItemListener {

                override fun onItemClicked(index: Int) {
                    viewModel.setItem(it[index])
                    findNavController().navigate(R.id.action_allItemsFragment_to_detailedItemFragment)
                }

                override fun onItemLongClick(index: Int) {
                }

            })
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("do nothing") //do nothing
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemAdapter = binding.recycler.adapter as ItemAdapter
                val position = viewHolder.adapterPosition
                val item = itemAdapter.itemAt(position)

                val deleteDialog: AlertDialog.Builder = AlertDialog.Builder(context)
                deleteDialog.apply {
                    setTitle(R.string.confirm_deletion)
                    setMessage(R.string.unfollow_stock_pormpt)
                    setCancelable(false)
                    setIcon(R.drawable.baseline_delete_outline_24)
                    setPositiveButton(R.string.yes) { _, _ ->
                        viewModel.deleteItem(item)
                        itemAdapter.notifyItemRemoved(position)
                    }
                    setNegativeButton(R.string.no) {_, _ ->
                        itemAdapter.notifyItemChanged(position) // restore
                    }
                }
                deleteDialog.create().show()
            }
        }).attachToRecyclerView(binding.recycler)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
