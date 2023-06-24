package com.example.stoks.ui.allitems

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.R
import com.example.stoks.data.local.ItemDao
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import kotlin.random.Random



@AndroidEntryPoint
class AllItemsFragment : Fragment(){

    private var _binding:AllItemsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ItemViewModel by activityViewModels()

    @Inject
    lateinit var stockRemoteDataSource: StockRemoteDataSource

    @Inject
    lateinit var itemDao: ItemDao

    @Inject
    lateinit var stockService: StockService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllItemsFragmentBinding.inflate(inflater,container,false)

//        binding.addItemButton.setOnClickListener{
//            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
//        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener {itemMenu ->
            when(itemMenu.itemId) {
                R.id.addItemButton -> {
                    findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
                    true
                }
                R.id.home -> {
                    // Respond to navigation item 2 click
                    true
                }
                R.id.favorite -> {
                    findNavController().navigate(R.id.action_allItemsFragment_to_favoriteItemsFragment)
                    true
                }
                else -> false
            }

        }

//        var numIteration = 0
        viewModel.items?.observe(viewLifecycleOwner) {
//            if (numIteration <= 1) {
//                val itemsList = viewModel.items?.value
//                itemsList?.forEach { item ->
//                    val symbol = item.stockSymbol
//                    val token = Constants.API_KEY
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        try {
//                            val response = stockRemoteDataSource.getQuote(symbol, token)
//                            if (response.status is Success) {
//                                val stockData = response.status.data
//                                val currPrice = stockData?.c
//                                Log.d("PRICES", "${symbol}: ${currPrice}")
//                                withContext(Dispatchers.Main) {
//                                    if (currPrice != null) {
//                                        item.currPrice = currPrice
//                                        itemDao.updateItem(item)
//                                    }
//                                }
//                            } else if (response.status is Error) {
//                                val errorMessage = response.status.message
//                                // Handle error response with the provided error message
//                            }
//                        } catch (e: Exception) {
//                            // Handle exception
//                        }
//                    }
//                }
//                numIteration++
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

    override fun onResume() {
        super.onResume()
        viewModel.items?.value?.let { items ->
            items.forEach { item ->
                val symbol = item.stockSymbol
                val token = Constants.API_KEY
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val response = stockRemoteDataSource.getQuote(symbol, token)
                        if (response.status is Success) {
                            val stockData = response.status.data
                            val currPrice = stockData?.c
                            withContext(Dispatchers.Main) {
                                if (currPrice != null) {
                                    item.currPrice = currPrice
                                    itemDao.updateItem(item)
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
        }
    }

}