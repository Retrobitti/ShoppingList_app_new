package com.example.shopping_app


import android.app.Application
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopping_app.components.AddItemDialog
import com.example.shopping_app.database.ShoppingListItem
import com.example.shopping_app.database.ShoppingListViewModel
import com.example.shopping_app.database.ShoppingListViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        viewModel = ShoppingListViewModelFactory(application).create(ShoppingListViewModel::class.java)

        setContent {
            ShoppingListApp(viewModel)
        }
    }
}

@Composable
fun ShoppingListApp(viewModel: ShoppingListViewModel) {
    var isAddItemDialogOpen by remember { mutableStateOf(false) }
    val shoppingListItems by viewModel.readAllData.observeAsState()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.padding(16.dp)) {
            shoppingListItems?.let { items ->
                items.forEach { item ->
                    Text(text = item.itemName)
                }
            }
            Button(onClick = { isAddItemDialogOpen = true }) {
                Text("Add Item")
            }

            if (isAddItemDialogOpen) {
                AddItemDialog(
                    viewModel = viewModel,
                    onCancel = { isAddItemDialogOpen = false }
                )
            }
        }
    }
}


