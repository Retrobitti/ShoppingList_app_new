package com.example.shopping_app.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel(application: Application) : AndroidViewModel(application){
    val readAllData: LiveData<List<ShoppingListItem>>
    private val repository: ShoppingListRepository

    init{
        val shoppingListDao = ShoppingListDatabase.getInstance(application).shoppingListDao()
        repository = ShoppingListRepository(shoppingListDao)
        readAllData = repository.readAllData
    }

    val itemsInBasket: LiveData<List<ShoppingListItem>> = repository.getAllInBasket

    val itemsNotInBasket: LiveData<List<ShoppingListItem>> = repository.getAllNotInBasket

    fun addShoppingListItem(shoppingListItem: ShoppingListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addShoppingListItem(shoppingListItem)
        }
    }

    fun updateShoppingListItem(shoppingListItem: ShoppingListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateShoppingListItem(shoppingListItem = shoppingListItem)
        }
    }

    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteShoppingListItem(shoppingListItem = shoppingListItem)
        }
    }

    fun deleteAllShoppingListItems(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllShoppingListItems()
        }
    }

    fun deleteShoppedItems(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteShoppedItems()
        }
    }

    fun deleteList(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteList()
        }
    }
}

class ShoppingListViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(ShoppingListViewModel::class.java)){
            return ShoppingListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}