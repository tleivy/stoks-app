package com.example.stoks.ui.additem

import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stoks.R
import com.example.stoks.data.model.StockLocalModel
import com.example.stoks.data.model.StockRemoteModel
import com.example.stoks.data.repository.StockRepository
import com.example.stoks.data.utils.Resource
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewStockViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<AddNewStockUiState> = MutableLiveData()
    val uiState: LiveData<AddNewStockUiState> = _uiState

    fun updateStockTicker(newTicker: String) {
        val updatedState = _uiState.value?.copy(stockTicker = newTicker) ?: AddNewStockUiState(
            selectedChip = null,
            stockTicker = newTicker,
            boughtAmount = "",
            boughtPrice = "",
            imageUri = null,
            errorMessage = null
        )
        _uiState.value = updatedState
    }

    fun updateBoughtAmount(newAmount: String) {
        val updatedState = _uiState.value?.copy(boughtAmount = newAmount) ?: AddNewStockUiState(
            selectedChip = null,
            stockTicker = "",
            boughtAmount = newAmount,
            boughtPrice = "",
            imageUri = null,
            errorMessage = null
        )
        _uiState.value = updatedState
    }

    fun updateBoughtPrice(newPrice: String) {
        val updatedState = _uiState.value?.copy(boughtPrice = newPrice) ?: AddNewStockUiState(
            selectedChip = null,
            stockTicker = "",
            boughtAmount = "",
            boughtPrice = newPrice,
            imageUri = null,
            errorMessage = null
        )
        _uiState.value = updatedState
    }

    fun onImageSelected(uri: Uri?) {
        val updatedState: AddNewStockUiState
        if (uri != null) {
            updatedState = _uiState.value?.copy(imageUri = uri) ?: AddNewStockUiState(
                selectedChip = null,
                stockTicker = "",
                boughtAmount = "",
                boughtPrice = "",
                imageUri = uri,
                errorMessage = null
            )
        } else {
            updatedState =
                _uiState.value?.copy(errorMessage = R.string.selectImage) ?: AddNewStockUiState(
                    selectedChip = null,
                    stockTicker = "",
                    boughtAmount = "",
                    boughtPrice = "",
                    imageUri = null,
                    errorMessage = R.string.selectImage
                )
        }
        _uiState.value = updatedState
    }

    fun nullifyImageUri() {
        val updatedState = _uiState.value?.copy(imageUri = null) ?: AddNewStockUiState(
            selectedChip = null,
            stockTicker = "",
            boughtAmount = "",
            boughtPrice = "",
            imageUri = null,
            errorMessage = null
        )
        _uiState.value = updatedState
    }

    fun onAddStockButtonClicked(
        companyName: String,
        stockTicker: String,
        boughtAmount: Long,
        boughtPrice: Double,
        companyImage: Uri?
    ) = viewModelScope.launch {

        val updatedExistingStock =
            repository.addToOwnedShares(companyName, boughtAmount, boughtPrice)

        val remoteServiceResource: Resource<StockRemoteModel> =
            repository.fetchRemoteStockData(stockTicker)

        if (!updatedExistingStock) {
            val currentPrice = remoteServiceResource.status.data?.currentPrice ?: 0.0
            val newStock = StockLocalModel(
                companyName, stockTicker, boughtPrice,
                currentPrice, boughtAmount, companyImage, false
            )
            repository.addNewStock(newStock)
        }
        _uiState.value = AddNewStockUiState(
            selectedChip = null,
            stockTicker = "",
            boughtAmount = "",
            boughtPrice = "",
            imageUri = null,
            errorMessage = null,
            appAddedSuccessfully = true
        )
    }

    data class AddNewStockUiState(
        val selectedChip: Chip? = null,
        val stockTicker: String? = null,
        val boughtAmount: String? = null,
        val boughtPrice: String? = null,
        var imageUri: Uri? = null,
        @StringRes val errorMessage: Int? = null,
        val appAddedSuccessfully: Boolean? = null
    )


}