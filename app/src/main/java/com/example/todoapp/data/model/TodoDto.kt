package com.example.todoapp.data.model

data class TodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
) {
    fun toTodo(): Todo {
        return Todo(
            id = id,
            userId = userId,
            title = title,
            completed = completed
        )
    }
}
