package com.example.stoks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stoks.databinding.AddItemFragmentBinding
import kotlin.random.Random

class AddItemFragment : Fragment(){
    private var _binding:AddItemFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemFragmentBinding.inflate(inflater, container, false)
        binding.addBtn.setOnClickListener{
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }

        val stockDataMaps = StocksDataMaps()
        val stockSymbols = stockDataMaps.stockSymbols
        val stockPrices = stockDataMaps.stockPrices
        var currPrice = 0.0
        binding.stockName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(newName: CharSequence?, start: Int, before: Int, count: Int) {
                val companyName = newName.toString()
                binding.stockSymbol.setText(stockSymbols[companyName])

                val pricesArr = stockPrices[companyName]
                val randomInt = Random.nextInt(3)  // A random int in 0-2
                currPrice = pricesArr?.get(randomInt) ?: 0.0
                binding.stockPrice.setText(currPrice?.toString() + "$")
            }
            override fun beforeTextChanged(newName: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(newName: Editable?) {}
        })

        binding.stockAmmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(newAmount: CharSequence?, start: Int, before: Int, count: Int) {
                val totalInvested = currPrice * binding.stockAmmount.text.toString().toInt()
                binding.stockTotalInvestment.setText(totalInvested.toString() + "$")
            }
            override fun beforeTextChanged(newName: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(newName: Editable?) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}