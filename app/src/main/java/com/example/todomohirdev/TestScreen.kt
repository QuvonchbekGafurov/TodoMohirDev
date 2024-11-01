package com.example.todomohirdev

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todomohirdev.data.Todo

@Composable
fun TodoTScreen(todoViewModel: TodoViewModel) {
    // allTodos-ni to'liq ro'yxat sifatida o'qib turish uchun StateFlow-ni kuzatamiz
    val allTodos by todoViewModel.allTodos.collectAsState(initial = emptyList())

    var todoTitle by remember { mutableStateOf("") }
    var todoHour by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // `Todo` qo'shish uchun forma
        OutlinedTextField(
            value = todoTitle,
            onValueChange = { todoTitle = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = todoHour,
            onValueChange = { todoHour = it },
            label = { Text("Hour (e.g., 10:30)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                // Yangi `Todo` yaratamiz va `insertTodo` orqali bazaga qo'shamiz
                if (todoTitle.isNotEmpty() && todoHour.isNotEmpty()) {
                    val newTodo = Todo(
                        date = "2024-10-30",
                        name = todoTitle,
                        title = todoTitle,
                        hour = todoHour,
                        checked = false
                    )
                    todoViewModel.insertTodo(newTodo)

                    // Qo'shilgandan keyin inputlarni tozalaymiz
                    todoTitle = ""
                    todoHour = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Todo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barcha `Todo` obyektlarini ro'yxat sifatida chiqaramiz
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allTodos) { todo ->
                TodoItem(todo)
            }
        }
    }
}

@Composable
fun TodoItem(todo: Todo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${todo.title} at ${todo.hour}")
        Checkbox(
            checked = todo.checked,
            onCheckedChange = null // Statik ro'yxatda ishlatiladi
        )
    }
}
