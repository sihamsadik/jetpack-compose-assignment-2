package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoDetailViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo

    fun loadTodo(todoId: Int) {
        viewModelScope.launch {
            _todo.value = repository.getTodoById(todoId)
        }
    }
    class Factory(private val repository: TodoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TodoListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}}
