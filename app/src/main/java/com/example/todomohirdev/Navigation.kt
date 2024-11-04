package com.example.todomohirdev

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todomohirdev.ui.AddTodo
import com.example.todomohirdev.ui.BottomAppBarWithFab
import com.example.todomohirdev.ui.EditTodo

@Composable
fun NavigationExample(viewModel: TodoViewModel) {
    val navController = rememberNavController() // NavController yaratish
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { BottomAppBarWithFab(navController = navController, todoViewModel = viewModel) }
        composable("addtodo") { backStackEntry ->
            var todoId = backStackEntry.arguments?.getString("todoId")
            AddTodo(navController = navController, todoViewModel = viewModel)
        }
        composable("edittodo/{todoId}") { backStackEntry ->
            var todoId = backStackEntry.arguments?.getString("todoId")
            EditTodo(navController = navController, todoViewModel = viewModel, id = todoId)
        }
    }
}