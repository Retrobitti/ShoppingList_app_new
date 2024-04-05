package com.example.shopping_app.components

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        title = { Text("Add New Item", color = Color.White, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,) },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = newItemName.value,
                    onValueChange = { newItemName.value = it },
                    label = { Text("Item Name") },
                    singleLine = true,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        },
        containerColor = Color(0xFFEE9322),
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
            }, modifier = Modifier
                .size(width = 40.dp, height = 40.dp)
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                .background(color = Color(0xFF219C90), shape = CircleShape),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    Icons.Default.Add, contentDescription = "Delete item",
                    modifier = Modifier.background(color = Color(0xFF219C90)))
            }
        },
        dismissButton = {
            Button(onClick = onCancel,
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .background(color = Color(0xFF219C90), shape = CircleShape),
                contentPadding = PaddingValues(0.dp)) {
                Icon(
                    Icons.Default.ArrowBack, contentDescription = "Delete item",
                    modifier = Modifier.background(color = Color(0xFF219C90)))
            }
        }
    )
}


