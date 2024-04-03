package com.example.shopping_app.database
import androidx.lifecycle.LiveData

class ShoppingListRepository(private  val shoppingListDao: ShoppingListDatabaseDao) {
    val readAllData : LiveData<List<ShoppingListItem>> = shoppingListDao.getAll()

    val getAllInBasket : LiveData<List<ShoppingListItem>> = shoppingListDao.getItemsInBasket()

    val getAllNotInBasket : LiveData<List<ShoppingListItem>> = shoppingListDao.getItemsNotInBasket()

    suspend fun addShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListDao.insert(shoppingListItem)
    }

    suspend fun updateShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListDao.update(shoppingListItem)
    }

    suspend fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListDao.delete(shoppingListItem)
    }

    suspend fun deleteAllShoppingListItems(){
        shoppingListDao.deleteAllItems()
    }

    suspend fun deleteShoppedItems(){
        shoppingListDao.deleteShoppedItems()
    }

    suspend fun deleteList(){
        shoppingListDao.deleteList()
    }
}