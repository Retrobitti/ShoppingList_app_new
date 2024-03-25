package com.example.shopping_app.database
import androidx.lifecycle.LiveData

class ShoppigListRepository(private  val shoppingListDao: ShoppingListDatabaseDao) {
    val readAllData : LiveData<List<ShoppingListItem>> = shoppingListDao.getAll()

    suspend fun addShoppingListitem(shoppingListItem: ShoppingListItem){
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
}