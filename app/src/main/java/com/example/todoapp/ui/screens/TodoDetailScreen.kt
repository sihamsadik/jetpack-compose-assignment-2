package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.TodoDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    navController: NavController,
    viewModel: TodoDetailViewModel,
    todoId: Int
) {
    // Load the TODO item when screen loads
    LaunchedEffect(todoId) {
        viewModel.loadTodo(todoId)
    }

    val todo by viewModel.todo.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODO Detail") }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .padding(contentPadding)
            .padding(16.dp)) {
            todo?.let {
                Column {
                    Text("Title: ${it.title}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Status: ${if (it.completed) "Completed" else "Pending"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } ?: run {
                CircularProgressIndicator()
            }
        }
    }
}