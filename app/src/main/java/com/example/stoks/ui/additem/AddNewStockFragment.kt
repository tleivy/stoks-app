package com.example.stoks.ui.additem

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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.stoks.R
import com.example.stoks.databinding.AddNewStockFragmentBinding
import com.example.stoks.data.local.StocksDataMaps
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewStockFragment : Fragment() {
    private var _binding: AddNewStockFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddNewStockViewModel by activityViewModels()

    private var lastUiState: AddNewStockViewModel.AddNewStockUiState? = null

    private val pickItemLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            viewModel.onImageSelected(uri)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNewStockFragmentBinding.inflate(inflater, container, false)

        val stockTickers = StocksDataMaps.stockTickers
        val stockImages = StocksDataMaps.stockImages
        var companyName = ""

        val chipGroup = binding.chipGroup
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedChip = group.findViewById<Chip>(checkedId)
            val selectedChipText = selectedChip?.text.toString()
            companyName = selectedChipText
            if (stockTickers.containsKey(companyName)) {
                binding.stockTicker.setText(stockTickers[companyName])
                binding.companyName.setText(companyName)
                Glide.with(this).load(stockImages[companyName]!!).into(binding.previewImage)
                viewModel.nullifyImageUri()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerObservers()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerObservers() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) { newState ->
            val oldState = lastUiState
            if (oldState != null) {
                if (newState.imageUri != oldState.imageUri) {
                    if (newState.imageUri != null) {
                        binding.previewImage.setImageURI(newState.imageUri)
                        requireActivity().contentResolver.takePersistableUriPermission(
                            newState.imageUri!!,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                }
                if (newState.errorMessage != oldState.errorMessage) {
                    if (newState.errorMessage != null) {
                        Toast.makeText(requireContext(), newState.errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                if (newState.appAddedSuccessfully != oldState.appAddedSuccessfully) {
                    if (newState.appAddedSuccessfully == true) {
                        findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
                    }
                }
            }
            lastUiState = newState
        }
    }

    private fun initListeners() {

        binding.stockTicker.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(inputTicker: Editable?) {
                viewModel.updateStockTicker(inputTicker.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

        binding.boughtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(inputAmount: Editable?) {
                viewModel.updateBoughtAmount(inputAmount.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

        binding.boughtPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(inputPrice: Editable?) {
                viewModel.updateBoughtPrice(inputPrice.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed
            }
        })

        binding.addStockButton.setOnClickListener {
            if (checkIfAllFieldsAreFilled()) {
                val uriString: Uri? = createImageUriString()
                viewModel.onAddStockButtonClicked(
                    binding.companyName.toString(),
                    binding.stockTicker.toString(),
                    binding.boughtAmount.text.toString().toLong(),
                    binding.boughtPrice.text.toString().toDouble(),
                    uriString
                )
            }
        }

        binding.resetButton.setOnClickListener {
            viewModel.nullifyImageUri()
            binding.companyName.setText("")
            binding.chipGroup.clearCheck()
            binding.stockTicker.setText("")
            binding.boughtPrice.setText("")
            binding.boughtAmount.setText("")
            binding.previewImage.setImageResource(R.drawable.app_logo)
        }

        binding.addImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }
    }

    private fun checkIfAllFieldsAreFilled(): Boolean {
        if (TextUtils.isEmpty(binding.boughtAmount.text?.toString())) {
            binding.boughtAmount.error = getString(R.string.fillAmount)
            return false
        }
        if (TextUtils.isEmpty(binding.boughtPrice.text?.toString())) {
            binding.boughtPrice.error = getString(R.string.fillPrice)
            return false
        }
        val selectedChipText =
            binding.chipGroup.findViewById<Chip>(binding.chipGroup.checkedChipId)?.text?.toString()
        if (selectedChipText.isNullOrEmpty()) {
            Toast.makeText(requireContext(), R.string.fillStock, Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun createImageUriString(): Uri? {
        val tempString: Uri? = if (viewModel.uiState.value?.imageUri != null) {
            viewModel.uiState.value?.imageUri
        } else {
            val resourceId: Int? = StocksDataMaps.stockImages[binding.companyName.text.toString()]
            Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + resourceId?.let { it1 -> resources.getResourcePackageName(it1) } +
                        "/" + resourceId?.let { it1 -> resources.getResourceTypeName(it1) } +
                        "/" + resourceId?.let { it1 -> resources.getResourceEntryName(it1) }
            )
        }
        return tempString
    }

    private fun nullifyImageUri() {
        viewModel.nullifyImageUri()
    }
}