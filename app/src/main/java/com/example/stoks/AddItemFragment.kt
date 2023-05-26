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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stoks.databinding.AddItemFragmentBinding
import java.io.InputStream
import kotlin.random.Random


class AddItemFragment : Fragment() {
    private var _binding: AddItemFragmentBinding? = null
    private val binding get() = _binding!!

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


        val stockDataMaps = StocksDataMaps()
        val stockSymbols = stockDataMaps.stockSymbols
        val stockPrices = stockDataMaps.stockPrices
        val stockImages = stockDataMaps.stockImages
        var currPrice = 0.0


        binding.stockName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                newName: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val companyName = newName.toString()
                if (stockSymbols[companyName] != null) {
                    binding.stockSymbol.setText(stockSymbols[companyName])
                    binding.previewImage.setImageResource(stockImages[companyName]!!)
                } else {
                    // TODO: API call -> get stock symbol, add to map
                }

                // TODO: Needs to be an API call
                val pricesArr = stockPrices[companyName]
                if (pricesArr != null) {
                    val randomInt = Random.nextInt(3)  // A random int in 0-2
                    currPrice = pricesArr?.get(randomInt) ?: 0.0
                    binding.stockPrice.setText(currPrice?.toString())
                }
            }

            override fun beforeTextChanged(
                newName: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(newName: Editable?) {}
        })

        binding.stockAmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                newAmount: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if(TextUtils.isEmpty(binding.stockAmount.text?.toString())){
                    binding.stockTotalInvestment.setText("0")
                }else {
                    val totalInvested = currPrice * binding.stockAmount.text.toString().toInt()
                    binding.stockTotalInvestment.setText(totalInvested.toString())
                }
            }

            override fun beforeTextChanged(
                newName: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(newName: Editable?) {}
        })


        binding.addBtn.setOnClickListener {
            val stockDefault = binding.stockName.text.toString()
            var tempstring: Uri?
            if (imageUri != null) {
                tempstring = imageUri
            } else {
                val resourceId: Int? = stockImages[stockDefault]
                tempstring = Uri.parse(
                    ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + resourceId?.let { it1 -> resources.getResourcePackageName(it1) } +
                            "/" + resourceId?.let { it1 -> resources.getResourceTypeName(it1) } +
                            "/" + resourceId?.let { it1 -> resources.getResourceEntryName(it1) }
                )
            }
            val item = Item(
                binding.stockName.text.toString(),
                binding.stockSymbol.text.toString(),
                binding.stockPrice.text.toString().toDouble(),
                binding.stockAmount.text.toString().toDouble(),
                tempstring
            )
            ItemManager.add(item)
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