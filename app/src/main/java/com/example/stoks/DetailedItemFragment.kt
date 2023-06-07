package com.example.stoks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.stocks.databinding.DetailItemLayoutBinding

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
            binding.itemSymbol.text = it.stockSymbol
            binding.itemPrice.text = it.stockPrice.toString()
            binding.itemAmount.text = it.stockAmount.toString()
            binding.itemAmountTotal.text =
                (it.stockPrice.toInt() * it.stockAmount.toInt()).toString()
            Glide.with(requireContext()).load(it.stockImage).circleCrop()
                .into(binding.itemImage)

        }



        super.onViewCreated(view, savedInstanceState)
    }



}