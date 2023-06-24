package com.example.stoks.ui.additem

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.stoks.R
import com.example.stoks.databinding.AddItemFragmentBinding
import com.example.stoks.ui.ItemViewModel
import com.example.stoks.data.local.StocksDataMaps
import com.example.stoks.data.model.Item
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddItemFragment : Fragment() {
    private var _binding: AddItemFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by activityViewModels()

    private var imageUri: Uri? = null


    val pickItemLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                binding.previewImage.setImageURI(it)
                requireActivity().contentResolver.takePersistableUriPermission(
                    it!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imageUri = it
            } else {
                Toast.makeText(requireContext(), R.string.selectImage, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemFragmentBinding.inflate(inflater, container, false)

        val stockSymbols = StocksDataMaps.stockSymbols
        val stockImages = StocksDataMaps.stockImages
        var companyName = ""

        val chipGroup = binding.chipGroup
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedChip = group.findViewById<Chip>(checkedId)
            val selectedChipText = selectedChip?.text.toString()
            companyName = selectedChipText
            if (stockSymbols.containsKey(companyName)) {
                binding.stockSymbol.setText(stockSymbols[companyName])
                binding.stockName.setText(companyName)
                Glide.with(this).load(stockImages[companyName]!!).into(binding.previewImage)
                imageUri = null
            }
        }

        binding.addBtn.setOnClickListener {
            //check fields
            var allFilled = true
            if (TextUtils.isEmpty(binding.stockAmount.text?.toString())) {
                allFilled = false
                binding.stockAmount.error = getString(R.string.fillAmount)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.stockPrice.text?.toString())) {
                allFilled = false
                binding.stockPrice.error = getString(R.string.fillPrice)
                return@setOnClickListener

            }
            val selectedChipText =
                binding.chipGroup.findViewById<Chip>(binding.chipGroup.checkedChipId)?.text?.toString()
            if (selectedChipText.isNullOrEmpty()) {
                allFilled = false
                Toast.makeText(requireContext(), R.string.fillStock, Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

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

            if (allFilled) {
                val item = Item(
                    binding.stockName.text.toString(),
                    binding.stockSymbol.text.toString(),
                    binding.stockPrice.text.toString().toDouble(),
                    binding.stockAmount.text.toString().toLong(),
                    tempstring,
                    0.0,
                    false
                )
                viewModel.addItem(item)
                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }

        binding.resetBtn.setOnClickListener {
            imageUri = null
            binding.stockName.setText("")
            binding.chipGroup.clearCheck()
            binding.stockSymbol.setText("")
            binding.stockPrice.setText("")
            binding.stockAmount.setText("")
            binding.previewImage.setImageResource(R.drawable.app_logo)
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