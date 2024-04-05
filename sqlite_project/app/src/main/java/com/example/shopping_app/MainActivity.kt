package com.example.shopping_app


import android.app.Application
import android.graphics.drawable.Icon
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Column(modifier = Modifier
            .fillMaxHeight()
            .background(color = Color(0xFF219C90))) {
            Text(
                text = "Shopping List", textAlign = TextAlign.Center,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,


                modifier = Modifier

                    .padding(bottom = 8.dp)
                    .background(color = Color(0xFFEE9322))
                    .size(1000.dp, 60.dp)
                    .padding(top = 10.dp)

            )
            shoppingListItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                            .background(
                                color = Color(0xFFE9B824),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text( text = item.itemName,
                            Modifier
                                .weight(1f)
                                .padding(start = 20.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,


                        )
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .weight(0.2f)
                        ){
                            Button(onClick = {
                                val updatedItem = item.copy(isInBasket = !item.isInBasket)
                                viewModel.updateShoppingListItem(updatedItem)
                            },
                                modifier = Modifier
                                    .size(width = 40.dp, height = 40.dp)
                                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                                    .background(color = Color(0xFFEE9322), shape = CircleShape),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.Check, contentDescription = "Delete item",
                                    modifier = Modifier.background(color = Color(0xFFEE9322)))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .weight(0.2f)
                        ){
                            Button(onClick = { viewModel.deleteShoppingListItem(item) },
                                modifier = Modifier
                                    .size(width = 40.dp, height = 40.dp)
                                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                                    .background(color = Color(0xFFD83F31), shape = CircleShape),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete item",
                                    modifier = Modifier.background(color = Color(0xFFD83F31)))
                            }
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

                Row (
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(start = 80.dp, end = 80.dp, bottom = 20.dp)
                        .background(
                            color = Color(0xFF219C90),
                        )){
                    Button(onClick = { isAddItemDialogOpen = true },
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .background(color = Color(0xFFEE9322), shape = CircleShape),
                        contentPadding = PaddingValues(0.dp)
                    ){
                        Icon(Icons.Default.Add,contentDescription = "Add Item",
                            modifier = Modifier.background(color = Color(0xFFEE9322)))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = onNavigateToShoppedItems,
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .background(color = Color(0xFFEE9322), shape = CircleShape),
                        contentPadding = PaddingValues(0.dp)
                    ){
                        Icon(Icons.Default.ShoppingCart,contentDescription = "Add Item",
                            modifier = Modifier.background(color = Color(0xFFEE9322)))

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { viewModel.deleteList() },
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .background(color = Color(0xFFEE9322), shape = CircleShape),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Done,contentDescription = "Add Item",
                            modifier = Modifier.background(color = Color(0xFFEE9322)))
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



@Composable
fun ItemsInBasket(navController: NavHostController, viewModel: ShoppingListViewModel, onNavigateBack: () -> Unit) {
    var isAddItemDialogOpen by remember { mutableStateOf(false) }
    val shoppedItems by viewModel.itemsInBasket.observeAsState()
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color(0xFF219C90))
        ) {
            Text(
                text = "Shopping List", textAlign = TextAlign.Center,
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontWeight = FontWeight.Bold,


                modifier = Modifier

                    .padding(bottom = 8.dp)
                    .background(color = Color(0xFFEE9322))
                    .size(1000.dp, 60.dp)
                    .padding(top = 10.dp)

            )
            shoppedItems?.let { items ->
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                            .background(
                                color = Color(0xFFE9B824),
                                shape = RoundedCornerShape(10.dp)
                            )

                    ) {
                        Text(text = item.itemName, modifier = Modifier
                            .weight(1f)
                            .padding(start = 20.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,)
                }
            }
                Spacer(modifier = Modifier.weight(1f))
                Row (
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(start = 80.dp, end = 80.dp, bottom = 20.dp)
                        .background(
                            color = Color(0xFF219C90),
                        )){
                    Button(onClick = onNavigateBack,
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .background(color = Color(0xFFEE9322), shape = CircleShape),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Home,contentDescription = "Add Item",
                            modifier = Modifier.background(color = Color(0xFFEE9322)))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = { viewModel.deleteShoppedItems() },
                        modifier = Modifier
                            .size(width = 40.dp, height = 40.dp)
                            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                            .background(color = Color(0xFFEE9322), shape = CircleShape),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Default.Delete,contentDescription = "Add Item",
                            modifier = Modifier.background(color = Color(0xFFEE9322)))
                    }
                }

        }
    }
    }
}