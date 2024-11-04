package com.example.todomohirdev.ui
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todomohirdev.TodoViewModel
import com.example.todomohirdev.data.Todo
import com.example.todomohirdev.theme.mainbackgroun
import java.util.Calendar
import kotlin.math.log

@Composable
fun EditTodo(todoViewModel: TodoViewModel, navController: NavController, id: String? =null){
    Log.e("TAG", "EditTodoID: $id", )
    var isInitialized by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var todo by remember { mutableStateOf<Todo?>(null) }
    var check by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("Sana tanlanmagan") }
    var selectedTime by remember { mutableStateOf("Vaqt tanlanmagan") }

    LaunchedEffect(id) {
        if (id != null) {
            todoViewModel.getTodoById(id.toInt()).collect { todo ->
                name = todo.name ?: ""
                title = todo.title ?: ""
                selectedDate = todo.date ?: "Sana tanlanmagan"
                selectedTime = todo.hour ?: "Vaqt tanlanmagan"
                check = todo.checked
            }
        }
    }
    val context= LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainbackgroun)
            .padding(16.dp),
    ) {
        // Ism kiritish maydoni
        androidx.compose.material.Text(text = "Name")
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            maxLines = 1,

            decorationBox = { innerTextField ->
                if (name.isEmpty()) {
                    Text("Ism kiritish", color = Color.Gray)
                }
                innerTextField()
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        androidx.compose.material.Text(text = "Title")
        Spacer(modifier = Modifier.height(8.dp))

        // Sarlavha kiritish maydoni
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
                .background(mainbackgroun)
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (title.isEmpty()) {
                    Text("Sarlavha kiritish", color = Color.Gray)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(8.dp))

        // Sana tanlash tugmasi
        Button(onClick = {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                },
                year,
                month,
                day
            ).show()
        }) {
            Text(selectedDate)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Vaqt tanlash tugmasi
        Button(onClick = {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(
                context,
                { _, selectedHour, selectedMinute ->
                    selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                },
                hour,
                minute,
                true
            ).show()
        }) {
            Text(selectedTime)
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Qo'shish tugmasi
        Button(
            onClick = {
                // Ism, sarlavha, sana va vaqtni tekshirish
                if (name.isNotBlank() && title.isNotBlank() && selectedDate != "Sana tanlanmagan" && selectedTime != "Vaqt tanlanmagan") {
                    println("Ism: $name, Sarlavha: $title, Sana: $selectedDate, Vaqt: $selectedTime")

                    var updatedTodo = Todo(
                        date = selectedDate,
                        name = name,
                        title = title,
                        hour = selectedTime,
                        checked = check
                    )
                    if (id != null) {
                        updatedTodo.id = id.toInt() // ID ni yangilash
                        todoViewModel.updateTodoTitle(updatedTodo)
                        Log.d("EditTodo", "Yangilandi: $updatedTodo")
                    }
                    navController.navigate("main")

                } else {
                    // Toast xabarini ko'rsatish
                    Toast.makeText(context, "Iltimos, barcha maydonlarni to'ldiring!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Qo'shish")
        }
    }
}

