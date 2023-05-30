package com.example.stoks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stocks.R
import com.example.stocks.databinding.AllItemsFragmentBinding


class AllItemsFragment : Fragment(){

    private var _binding:AllItemsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = AllItemsFragmentBinding.inflate(inflater,container,false)


        binding.addItemButton.setOnClickListener{
            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_delete){
            viewModel.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel.items?.observe(viewLifecycleOwner) {


            binding.recycler.adapter = ItemAdapter(it, object : ItemAdapter.ItemListener {

                override fun onItemClicked(index: Int) {
                    //Toast.makeText(requireContext(),it[index].toString(),Toast.LENGTH_SHORT).show()
                    viewModel.setItem(it[index])
                    findNavController().navigate(R.id.action_allItemsFragment_to_detailedItemFragment)
                }

                override fun onItemLongClick(index: Int) {
                    Toast.makeText(requireContext(),it[index].toString(),Toast.LENGTH_SHORT).show()
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
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                viewModel.deleteItem((binding.recycler.adapter as ItemAdapter)
                    .itemAt(viewHolder.adapterPosition))
                binding.recycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)

            }

        }).attachToRecyclerView(binding.recycler)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}