package com.example.stoks.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stoks.data.model.Item
import com.example.stoks.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    val items: LiveData<List<Item>> = repository.getItems()

    val favorites: LiveData<List<Item>> = repository.getFavorites()

    private val _chosenItem = MutableLiveData<Item>()

    val chosenItem: LiveData<Item> get() = _chosenItem


    fun setItem(item: Item) {
        _chosenItem.value = item
    }

    fun addItem(item: Item) {
        viewModelScope.launch {
            repository.addItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun getTotalAmountForStock(stockName: String) {
        viewModelScope.launch {
            repository.getTotalAmountForStock(stockName)
        }
    }

    fun removeFromFavorites(item: Item) {
        viewModelScope.launch {
            repository.removeFromFavorites(item)
        }
    }

    fun addToFavorites(item: Item) {
        viewModelScope.launch {
            repository.addToFavorites(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }

}