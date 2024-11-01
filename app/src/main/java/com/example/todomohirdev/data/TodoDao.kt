package com.example.todomohirdev.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Query("DELETE FROM todo_table WHERE id = :id")
    suspend fun deleteTodoById(id: Int)

    @Query("SELECT * FROM todo_table WHERE date = :date ORDER BY hour ASC")
    fun getTodosByDate(date: String): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table ORDER BY date ASC, hour ASC")
    fun getAllTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE id = :id LIMIT 1")
    suspend fun getTodoById(id: Int): Todo?
}
