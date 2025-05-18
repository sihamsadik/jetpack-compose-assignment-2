package com.example.todoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.TodoListScreen
import com.example.todoapp.ui.screens.TodoDetailScreen
import com.example.todoapp.viewmodel.TodoListViewModel
import com.example.todoapp.viewmodel.TodoDetailViewModel

@Composable
fun AppNavigation(
    todoListVM: TodoListViewModel,
    todoDetailVM: TodoDetailViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "todo_list") {

        composable("todo_list") {
            TodoListScreen(viewModel = todoListVM, navController = navController)
        }

        composable(
            route = "todo_detail/{todoId}",
            arguments = listOf(navArgument("todoId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
            TodoDetailScreen(
                navController = navController,
                viewModel = todoDetailVM,
                todoId = todoId
            )
        }
    }
}
