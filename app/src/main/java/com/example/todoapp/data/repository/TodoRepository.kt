package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TodoDao
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(
    private val apiService: ApiService,
    private val todoDao: TodoDao
) {
    suspend fun getTodos(): List<Todo> = withContext(Dispatchers.IO) {
        return@withContext try {
            val todoDtos = apiService.getTodos()
            val todos = todoDtos.map { it.toTodo() }
            todoDao.insertTodos(todos)
            todos
        } catch (e: Exception) {
            todoDao.getAllTodos()
        }
    }

    suspend fun getTodoById(id: Int): Todo? = withContext(Dispatchers.IO) {
        return@withContext todoDao.getTodoById(id)
    }
}
