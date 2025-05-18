package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.data.local.TodoDatabase
import com.example.todoapp.data.remote.ApiService
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.ui.navigation.AppNavigation
import com.example.todoapp.viewmodel.TodoDetailViewModel
import com.example.todoapp.viewmodel.TodoListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Room database and DAO
        val todoDao = TodoDatabase.getDatabase(applicationContext).todoDao()

        // Initialize Retrofit API service
        val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // Replace with your actual API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Create the repository
        val repository = TodoRepository(apiService, todoDao)

        // ViewModel factories
        class TodoListViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TodoListViewModel(repository) as T
            }
        }

        class TodoDetailViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TodoDetailViewModel(repository) as T
            }
        }

        setContent {
            val todoListViewModel: TodoListViewModel = viewModel(factory = TodoListViewModelFactory(repository))
            val todoDetailViewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModelFactory(repository))

            AppNavigation(
                todoListVM = todoListViewModel,
                todoDetailVM = todoDetailViewModel
            )
        }
    }
}