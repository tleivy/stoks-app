package com.example.stoks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stoks.databinding.AllItemsFragmentBinding
import java.util.Objects


class AllItemsFragment : Fragment(){
    private var _binding:AllItemsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AllItemsFragmentBinding.inflate(inflater, container, false)
        binding.addItemButton.setOnClickListener{
            findNavController().navigate(R.id.action_allItemsFragment_to_addItemFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.recycler.adapter = ItemAdapter(ItemManager.items , object :ItemAdapter.ItemListener{
            // TODO: open detailed item fragment
            override fun onItemClicked(index: Int) {
                Toast.makeText(requireContext(),ItemManager.items[index].toString(),Toast.LENGTH_SHORT).show()
            }

            // TODO: decide if necessary
            override fun onItemLongClick(index: Int) {
                Toast.makeText(requireContext(),ItemManager.items[index].toString(),Toast.LENGTH_SHORT).show()
            }
        })

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE , ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {return false}

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // TODO: pop confirmation dialog
                ItemManager.remove(viewHolder.adapterPosition)
                binding.recycler.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)  // visual update
            }


        }).attachToRecyclerView(binding.recycler)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}