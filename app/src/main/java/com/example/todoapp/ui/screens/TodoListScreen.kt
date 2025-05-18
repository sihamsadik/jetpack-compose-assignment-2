package com.example.todoapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.viewmodel.TodoListViewModel
import androidx.compose.material3.Scaffold


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    navController: NavController
) {
    val todos by viewModel.todos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TODO List") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                error != null -> {
                    Text("Error: $error", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    LazyColumn {
                        items(todos) { todo ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate("todo_detail/${todo.id}")
                                    },
                                colors = CardDefaults.cardColors(
//                                    containerColor = MaterialTheme.colorScheme.copy(alpha = 0.2f), // more distinct background
                                    contentColor = MaterialTheme.colorScheme.onSecondary // contrasting text color
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(todo.title, style = MaterialTheme.typography.titleMedium)
                                    Text(if (todo.completed) "Completed" else "Pending")
                                }
                            }
                            }
                            }
                        }
                    }
                }
            }
        }


