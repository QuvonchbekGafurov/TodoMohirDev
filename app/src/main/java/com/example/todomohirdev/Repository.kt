package com.example.todomohirdev

import com.example.todomohirdev.data.Todo
import com.example.todomohirdev.data.TodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {

    // Barcha todolarni qaytaruvchi funksiyani dao orqali olish
    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    // Todo qo'shish funksiyasi
    suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    // Todo yangilash funksiyasi
    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    // Todo o'chirish funksiyasi
    suspend fun deleteTodoById(id: Int) {
        todoDao.deleteTodoById(id)
    }

    // Sana bo'yicha todolarni olish funksiyasi
    fun getTodosByDate(date: String): Flow<List<Todo>> = todoDao.getTodosByDate(date)
}
