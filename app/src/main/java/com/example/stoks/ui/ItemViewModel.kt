package com.example.stoks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stoks.data.model.Stock
import com.example.stoks.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    val items: LiveData<List<Stock>> = repository.getItems()

    val favorites: LiveData<List<Stock>> = repository.getFavorites()

    private val _chosenItem = MutableLiveData<Stock>()

    val chosenItem: LiveData<Stock> get() = _chosenItem


    fun setItem(item: Stock) {
        _chosenItem.value = item
    }

    fun addItem(item: Stock) {
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: Stock) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun removeFromFavorites(item: Stock) {
        viewModelScope.launch {
            repository.removeFromFavorites(item)
        }
    }

    fun addToFavorites(item: Stock) {
        viewModelScope.launch {
            repository.addToFavorites(item)
        }
    }

    fun updateItem(item: Stock) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

}