package com.example.shopping_app.components

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopping_app.database.ShoppingListItem
import com.example.shopping_app.database.ShoppingListViewModel

@Composable
fun AddItemDialog(
    viewModel: ShoppingListViewModel, // Inject ShoppingListViewModel
    onCancel: () -> Unit
) {
    // State to hold the value of the new item name
    val newItemName = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Add New Item") },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                // Input field for new item name
                OutlinedTextField(
                    value = newItemName.value,
                    onValueChange = { newItemName.value = it },
                    label = { Text("Item Name") },
                    singleLine = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Add item to database through ViewModel
                viewModel.addShoppingListItem(
                    ShoppingListItem(
                        itemName = newItemName.value,
                        isInBasket = false
                    )
                )
                onCancel()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}


