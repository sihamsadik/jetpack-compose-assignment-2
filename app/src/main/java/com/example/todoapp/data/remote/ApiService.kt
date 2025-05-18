package com.example.todoapp.data.remote

import com.example.todoapp.data.model.TodoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("todos")
    suspend fun getTodos(): List<TodoDto>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): TodoDto
}
