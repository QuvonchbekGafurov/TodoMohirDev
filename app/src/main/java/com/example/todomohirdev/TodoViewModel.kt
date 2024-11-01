package com.example.todomohirdev

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    fun deleteTodo(id:Int) {
        viewModelScope.launch {
            repository.deleteTodoById(id)
        }
    }

    private val _todoItem = MutableLiveData<Todo?>()
    val todoItem: LiveData<Todo?> get() = _todoItem

    fun getTodoById(todoId: Int) {
        viewModelScope.launch {
            val todo = repository.getTodoById(todoId)
            _todoItem.value = todo
        }
    }
    fun updateTodoTitle(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo =todo )
        }
    }
}