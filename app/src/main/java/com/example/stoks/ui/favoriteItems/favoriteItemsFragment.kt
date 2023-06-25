package com.example.stoks.ui.allitems

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.R
import com.example.stoks.data.local.ItemDao
import com.example.stoks.data.remote_db.StockRemoteDataSource
import com.example.stoks.databinding.FavoriteItemsFragmentBinding
import com.example.stoks.ui.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteItemsFragment : Fragment() {

    private var _binding: FavoriteItemsFragmentBinding? = null
    private val binding get() = _binding!!
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
        _binding = FavoriteItemsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setSelectedItemId(R.id.favorite)
        binding.bottomNavigation.setOnItemSelectedListener { itemMenu ->
            when (itemMenu.itemId) {
                R.id.addItemButton -> {
                    findNavController().navigate(R.id.action_favoriteItemsFragment_to_addItemFragment)
                    true
                }

                R.id.home -> {
                    findNavController().navigate(R.id.action_favoriteItemsFragment_to_allItemsFragment)
                    true
                }

                R.id.favorite -> {
                    // Respond to navigation item 2 click
                    true
                }

                else -> false
            }

        }

        viewModel.favorites?.observe(viewLifecycleOwner) { favorites ->
            binding.emptyListTextView.visibility =
                if (favorites.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.recycler.adapter = ItemAdapter(favorites, object : ItemAdapter.ItemListener {
                override fun onItemClicked(index: Int) {
                    viewModel.setItem(favorites[index])
                    findNavController().navigate(R.id.action_favoriteItemsFragment_to_detailedItemFragment)
                }

                override fun onItemLongClick(index: Int) {
                    // do nothing
                }
            }, viewModel)

            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        }

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            )

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
                    setNegativeButton(R.string.no) { _, _ ->
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