package com.example.todomohirdev

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomohirdev.data.Todo
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val allTodos: Flow<List<Todo>> = repository.getAllTodos()

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }
}