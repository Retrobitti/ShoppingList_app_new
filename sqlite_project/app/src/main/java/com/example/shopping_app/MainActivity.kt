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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopping_app.components.AddItemDialog
import com.example.shopping_app.database.ShoppingListItem
import com.example.shopping_app.database.ShoppingListViewModel
import com.example.shopping_app.database.ShoppingListViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ShoppingListViewModelFactory(application).create(ShoppingListViewModel::class.java)

        setContent {
            ShoppingListApp(viewModel)
        }
    }
}
@Composable
fun ShoppingListApp(viewModel: ShoppingListViewModel){
    Surface(color = MaterialTheme.colorScheme.background) {
        val navController = rememberNavController()
        val currentScreen = remember { mutableStateOf("itemsNotInBasket") }

        when(currentScreen.value){
            "itemsNotInBasket" -> ItemsNotInBasket(navController= navController, viewModel = viewModel, onNavigateToShoppedItems = { currentScreen.value = "itemsInBasket" })
            "itemsInBasket" -> ItemsInBasket(navController = navController, viewModel = viewModel, onNavigateBack = { currentScreen.value = "itemsNotInBasket" })
        }
    }


}
@Composable
fun ItemsNotInBasket(navController: NavHostController, viewModel: ShoppingListViewModel,  onNavigateToShoppedItems: () -> Unit) {
    var isAddItemDialogOpen by remember { mutableStateOf(false) }
    val shoppingListItems by viewModel.itemsNotInBasket.observeAsState()
    val shoppedItems by viewModel.itemsInBasket.observeAsState()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column() {
            Text(
                text = "Shopping List", textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,

                modifier = Modifier

                    .padding(bottom = 8.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .size(1000.dp, 48.dp)
                    .padding(16.dp)

            )
            shoppingListItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 90.dp)
                    ) {
                        Text(text = item.itemName, )
                        Button(onClick = { viewModel.deleteShoppingListItem(item) },
                            Modifier.padding(start = 8.dp)) {
                            Text(text = "Delete")

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

            Button(onClick = { isAddItemDialogOpen = true },
                Modifier.padding(start = 125.dp)

                ){
                Text("Add Item")
            }
            Button(onClick = onNavigateToShoppedItems,
                Modifier.padding(start = 90.dp)
            ){
                Text("View Shopped Items")

            }
            Button(onClick = { viewModel.deleteList() },
                Modifier.padding(start = 92.5.dp)
            ) {
                Text(text = "Empty shopping list")
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


@Composable
fun ItemsInBasket(navController: NavHostController, viewModel: ShoppingListViewModel, onNavigateBack: () -> Unit) {
    var isAddItemDialogOpen by remember { mutableStateOf(false) }
    val shoppedItems by viewModel.itemsInBasket.observeAsState()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column() {
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
            shoppedItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp),

                    ) {
                        Text(text = item.itemName, textAlign = TextAlign.Right,
                            modifier = Modifier.padding(start = 16.dp))
                }
            }
            Button(onClick = onNavigateBack,
                Modifier.padding(start = 95.dp)
            ) {
                Text(text = "View shopping list")
            }
            Button(onClick = { viewModel.deleteShoppedItems() },
                Modifier.padding(start = 80.dp)
            ) {
                Text(text = "Delete shopping history")
            }
        }
    }
    }
}