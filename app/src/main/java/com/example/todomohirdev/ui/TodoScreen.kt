package com.example.todomohirdev.ui
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.todomohirdev.R
import com.example.todomohirdev.TodoItem
import com.example.todomohirdev.TodoViewModel
import com.example.todomohirdev.data.Todo
import com.example.todomohirdev.theme.jigar
import com.example.todomohirdev.theme.mainbackgroun
import com.example.todomohirdev.theme.maincolor
import com.example.todomohirdev.utils.ProfileImagePicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.runtime.remember as remember

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(todoViewModel: TodoViewModel,navController: NavController) {
    val allTodos by todoViewModel.allTodos.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }
    // Qidiruv natijalarini filtrlaymiz
    val filteredTodos = allTodos.filter { todo ->
        todo.name?.contains(searchQuery, ignoreCase = true) == true ||
                todo.title?.contains(searchQuery, ignoreCase = true) == true
    }

    Log.e("TAG", "AllTodos:  $allTodos ", )
    val selectedIndex = remember { mutableStateOf(3) }

    val currentDate = LocalDate.now()
    Log.e("TAG", "TodoScreen: $currentDate", )

    fun week(): List<String> {
        val today = LocalDate.now()
        val daysOfWeek = mutableListOf<String>()

        // 3 kun oldin va 3 kun keyin
        for (i in -3..3) {
            val day = today.plusDays(i.toLong())
            val dayOfWeek = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).capitalize()
            daysOfWeek.add(dayOfWeek)
        }

        return daysOfWeek
    }
    fun getWeekDates(): List<String> {
        val today = LocalDate.now() // Hozirgi sanani olish
        val weekDates = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("d-MMM") // Sana formatini belgilash

        // 3 kun oldin va 3 kun keyin
        for (i in -3..3) {
            val date = today.plusDays(i.toLong())
            weekDates.add(date.format(formatter))
        }

        return weekDates
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(mainbackgroun)
            .padding(16.dp)
    ) {
        // Profil va Salomlashuv
       Column(horizontalAlignment = Alignment.Start) {
           ProfileImagePicker()
           Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Good evening, Ivy",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        // Kalendar qismi
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val weeks = week().toMutableList()
            var days = getWeekDates().toMutableList()
            weeks[3]="Today"

            item {
                days.forEachIndexed { index, day ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(if (selectedIndex.value == index) 50.dp else 35.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .background(if (selectedIndex.value == index) maincolor else jigar) // Bosilgan element sariq rangga o'zgaradi
                                .clickable {
                                    selectedIndex.value = index
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${days[index]}",
                                color =Color.White,
                                fontSize = if (selectedIndex.value == index) 13.sp else 6.sp
                            )
                        }
                        Text(text = weeks[index], color = jigar, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Qidiruv
        TextField(
            value = searchQuery,
            shape = RoundedCornerShape(30.dp),
            onValueChange = {searchQuery = it},
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(1.dp, Color.LightGray, RoundedCornerShape(30.dp)), // Add black border
            placeholder = { Text("Search") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = null) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bugungi vazifalar
        Text("Today's tasks", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredTodos) { todo ->
                TaskItem(todo,todoViewModel,navController)
            }
        }
    }
}


@Composable
fun TaskItem(todo: Todo,todoViewModel: TodoViewModel,navController: NavController) {
    var isChecked by remember { mutableStateOf(todo.checked) }
    var selectedTodo by remember { mutableStateOf<Todo?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(20.dp)) // Qora rangdagi chegara
            .background(Color.Transparent)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked= checked
                todoViewModel.updateTodoTitle(todo.copy(checked = isChecked))
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFD7B600),
                uncheckedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "${todo.date} ${todo.hour}", fontSize = 14.sp, color = Color.Gray)
            Text(
                text = todo.name,
                fontSize = 16.sp,
                textDecoration = if (isChecked) TextDecoration.LineThrough else null
            )
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Icon(
                painter = painterResource(id = R.drawable.baseline_edit_document_24),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {

                        navController.navigate("edittodo/${todo.id}")
                    }
                ,
                tint = Color(0xFFD7B600)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        todoViewModel.deleteTodo(todo.id)
                    }
                ,
                tint = Color(0xFFD7B600)
            )
        }
    }
}
