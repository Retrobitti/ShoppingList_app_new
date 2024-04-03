package com.example.shopping_app


import android.app.Application
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    val shoppingListItems by viewModel.itemsNotInBasket.observeAsState()
    val shoppedItems by viewModel.itemsInBasket.observeAsState()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column() {
            // Title for Shopping List section
            Text(
                text = "Shopping List", textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,

                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .size(1000.dp, 48.dp)
                    .padding(16.dp)

            )
            // Display items not in the basket
            shoppingListItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = item.itemName, )
                        Modifier.padding(8.dp)

                        Button(onClick = { viewModel.deleteShoppingListItem(item) }) {
                            Text(text = "Delete")
                            Modifier.padding(8.dp)


                        }
                        Checkbox(checked = item.isInBasket,
                            onCheckedChange = {isChecked ->
                                val updatedItem = item.copy(isInBasket = isChecked)
                                viewModel.updateShoppingListItem(updatedItem)
                            }
                        )
                    }
                }
            }
            // Add item button for items not in the basket
            Button(onClick = { isAddItemDialogOpen = true }) {
                Text("Add Item")
                Modifier.padding(16.dp)

            }


            // Spacer for additional space between sections
            Spacer(modifier = Modifier.height(16.dp))

            // Title for Shopped Items section
            Text(
                text = "Shopped Items", textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)

            )
            // Display items in the basket
            shoppedItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp),

                    ) {
                        Text(text = item.itemName, textAlign = TextAlign.Right)

                    }
                }
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


