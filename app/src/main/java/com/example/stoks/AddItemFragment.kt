package com.example.stoks

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.stocks.R
import com.example.stocks.databinding.AddItemFragmentBinding
import kotlin.random.Random


class AddItemFragment : Fragment() {
    private var _binding: AddItemFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by activityViewModels()

    private var imageUri: Uri? = null


    val pickItemLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.previewImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(
                it!!,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemFragmentBinding.inflate(inflater, container, false)

        val stockSymbols = StocksDataMaps.stockSymbols
        val stockImages = StocksDataMaps.stockImages
        var currPrice = 0.0
        var companyName = ""

        val stockNames = stockSymbols.keys.toMutableList()
        val searchField = binding.searchField
        val namesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, stockNames)
        searchField.setAdapter(namesAdapter)
        searchField.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            companyName = selectedItem
            if (stockSymbols.containsKey(companyName)) {
                binding.stockSymbol.setText(stockSymbols[companyName])
                binding.stockName.setText(companyName)
                binding.previewImage.setImageResource(stockImages[companyName]!!)
//                // TODO: Needs to be an API call
//                val pricesArr = stockPrices[companyName]
//                if (pricesArr != null) {
//                    val randomInt = Random.nextInt(3)  // A random int in 0-2
//                    currPrice = pricesArr[randomInt] ?: 0.0
//                    binding.stockPrice.setText(currPrice.toString())
//                }
            } else {
                searchField.error = "Invalid selection"
                // TODO: API call -> get stock symbol, add to map
            }
        }

//        binding.stockAmount.addTextChangedListener(object : TextWatcher {
//            override fun onTextChanged(
//                newAmount: CharSequence?,
//                start: Int,
//                before: Int,
//                count: Int
//            ) {
//                if(TextUtils.isEmpty(binding.stockAmount.text?.toString())){
//                    binding.stockTotalInvestment.setText("0")
//                } else {
//                    val totalInvested = currPrice * binding.stockAmount.text.toString().toInt()
//                    binding.stockTotalInvestment.setText(totalInvested.toString())
//                }
//            }
//
//            override fun beforeTextChanged(
//                newName: CharSequence?,
//                start: Int,
//                count: Int,
//                after: Int
//            ) {
//            }
//
//            override fun afterTextChanged(newName: Editable?) {}
//        })

        binding.addBtn.setOnClickListener {
            var tempstring: Uri?
            if (imageUri != null) {
                tempstring = imageUri
            } else {
                val resourceId: Int? = stockImages[companyName]
                tempstring = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + resourceId?.let { it1 -> resources.getResourcePackageName(it1) } +
                            "/" + resourceId?.let { it1 -> resources.getResourceTypeName(it1) } +
                            "/" + resourceId?.let { it1 -> resources.getResourceEntryName(it1) }
                )
            }

            if (TextUtils.isEmpty(binding.stockAmount.text?.toString())) {
                binding.stockAmount.setText("0")
            }
            if (TextUtils.isEmpty(binding.stockPrice.text?.toString())) {
                binding.stockAmount.setText("0.0")
            }
            val item = Item(
                binding.stockName.text.toString(),
                binding.stockSymbol.text.toString(),
                binding.stockPrice.text.toString().toDouble(),
                binding.stockAmount.text.toString().toInt(),
                tempstring,
                0.0
            )
            viewModel.addItem(item)
            findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
        }

        binding.resetBtn.setOnClickListener {
            binding.stockName.setText("")
            binding.stockSymbol.setText("")
            binding.stockPrice.setText("")
            binding.stockAmount.setText("")
            binding.previewImage.setImageResource(R.mipmap.ic_launcher)
        }

        binding.imageBtn.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}