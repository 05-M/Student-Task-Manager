// MainActivity.kt - The main entry point of the application

package com.mido.studenttaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mido.studenttaskmanager.ui.theme.StudentTaskManagerTheme
import com.mido.studenttaskmanager.viewmodel.AppViewModelProvider
import com.mido.studenttaskmanager.viewmodel.TaskViewModel

/**
 * [MainActivity] serves as the primary entry point for the application.
 * It initializes the user interface (UI) using Jetpack Compose
 * and manages navigation between the main screens (Task List, Add New Task).
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created.
     * This function sets up the fundamental UI and applies the application's theme.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables "Edge-to-Edge" mode, allowing the UI to extend across the entire screen.
        enableEdgeToEdge()
        // Sets the content of the activity's user interface using Jetpack Compose.
        setContent {
            // Applies the custom theme for the application, which supports automatic light/dark mode.
            StudentTaskManagerTheme {
                // Surface is a Composable used to apply the background color from the theme and fill the screen.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Instantiates [TaskViewModel], the intermediary providing data and interacting with the database.
                    val taskViewModel: TaskViewModel =
                        viewModel(factory = AppViewModelProvider.Factory)
                    // Collects all tasks from the ViewModel as a State, ensuring UI updates automatically
                    // whenever the list of tasks changes in the database.
                    val taskLists by taskViewModel.allTasks.collectAsState(initial = emptyList())
                    // Creates and remembers a NavController to manage navigation between app screens.
                    val navController = rememberNavController()

                    // [NavHost] is the container that displays different screens based on the current navigation route.
                    NavHost(
                        navController = navController, // Links NavHost with NavController for navigation management.
                        startDestination = "task_list_screen" // Defines the default screen to display on app startup.
                    ) {
                        // Defines the first screen: the task list.
                        composable("task_list_screen") {
                            TaskListScreen(
                                taskList = taskLists, // Passes the current list of tasks.
                                navController = navController // Passes NavController to enable navigation from this screen.
                            )
                        }
                        // Defines the second screen: adding a new task.
                        composable("add_task_screen") {
                            AddTaskScreen(navController = navController) // Passes NavController to enable going back.
                        }
                    }
                }
            }
        }
    }
}