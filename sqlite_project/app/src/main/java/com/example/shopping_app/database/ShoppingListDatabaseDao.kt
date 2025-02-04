package com.example.shopping_app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ShoppingListDatabaseDao {
    @Query("SELECT * from shopping_list")
    fun getAll(): LiveData<List<ShoppingListItem>>

    @Query("SELECT * from shopping_list where ItemId = :id")
    fun getById(id: Int) : ShoppingListItem?

    @Query("SELECT * from shopping_list where `added_to_basket` = 0")
    fun getItemsNotInBasket():LiveData<List<ShoppingListItem>>

    @Query("SELECT * from shopping_list where `added_to_basket` = 1")
    fun getItemsInBasket():LiveData<List<ShoppingListItem>>
    @Insert
    suspend fun insert(item: ShoppingListItem)

    @Update
    suspend fun update(item: ShoppingListItem)

    @Delete
    suspend fun delete(item: ShoppingListItem)

    @Query("DELETE FROM shopping_list")
    suspend fun deleteAllItems()

    @Query("DELETE FROM shopping_list WHERE added_to_basket IS 1")
    suspend fun deleteShoppedItems()

    @Query("DELETE FROM shopping_list WHERE added_to_basket IS 0")
    suspend fun deleteList()
}