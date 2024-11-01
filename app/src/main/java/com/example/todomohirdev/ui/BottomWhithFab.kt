package com.example.todomohirdev.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.todomohirdev.TodoViewModel
import com.example.todomohirdev.theme.bottomnavigation
import com.example.todomohirdev.theme.maincolor

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomAppBarWithFab(todoViewModel: TodoViewModel, navController: NavController) {
    val content = remember { mutableStateOf("Home Screen") }
    val selectedItem = remember { mutableStateOf("home") }
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(

        content = {
           TodoScreen(todoViewModel,navController)
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openDialog.value = true
                    navController.navigate("addtodo")
                },
                shape = RoundedCornerShape(50),
                backgroundColor = maincolor
            ) {
                Icon(Icons.Filled.Add, tint = Color.White, contentDescription = "Add")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center, //if its not set, it's show default position

        bottomBar = {
            BottomAppBar(
                backgroundColor = bottomnavigation,
                cutoutShape = RoundedCornerShape(50),
                content = {
                    BottomNavigation(backgroundColor = bottomnavigation)
                    {

                        BottomNavigationItem(
                            selected = selectedItem.value == "home",
                            onClick = {
                                content.value = "Home Screen"
                                selectedItem.value = "home"
                            },
                            icon = {
                                Icon(Icons.Filled.Home,contentDescription = "home")
                            },
                            label = { Text(text = "Home") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black, // Bosilganda qora
                        )

                        BottomNavigationItem(
                            selected = selectedItem.value == "calendar",
                            onClick = {
                                content.value = "Calendar Screen"
                                selectedItem.value = "calendar"
                            },
                            icon = {
                                Icon(Icons.Filled.DateRange,contentDescription = "calendar")
                            },
                            label = { Text(text = "Calendar") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black, // Bosilganda qora
                        )
                        BottomNavigationItem(
                            selected = selectedItem.value == "notification",
                            onClick = {
                                content.value = "Notification Screen"
                                selectedItem.value = "notification"
                            },
                            icon = {
                                Icon(Icons.Outlined.Notifications,contentDescription = "notification")
                            },
                            label = { Text(text = "notification") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black, // Bosilganda qora
                        )
                        BottomNavigationItem(
                            selected = selectedItem.value == "menu",
                            onClick = {
                                content.value = "Menu Screen"
                                selectedItem.value = "menu"
                            },
                            icon = {
                                Icon(Icons.Default.Menu,contentDescription = "menu")
                            },
                            label = { Text(text = "Menu") },
                            alwaysShowLabel = false,
                            selectedContentColor = Color.Black, // Bosilganda qora
                        )
                    }
                }
            )
        }
    )
}