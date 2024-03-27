package com.example.shopping_app.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "shopping_list")
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long =0L,

    @ColumnInfo(name = "item_name")
    val itemName: String,

    @ColumnInfo(name = "added_to_basket")
    var isInBasket: Boolean = false
)
