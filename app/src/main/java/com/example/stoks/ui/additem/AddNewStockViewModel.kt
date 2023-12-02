package com.example.stoks.ui.additem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stoks.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNewStockViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState: MutableLiveData<AddNewStockUiState> = MutableLiveData()
    val uiState: LiveData<AddNewStockUiState> = _uiState




    data class AddNewStockUiState(
        val error: String? = null
    )

}