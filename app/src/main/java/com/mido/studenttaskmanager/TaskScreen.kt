// TaskListScreen.kt - The main screen for displaying the list of tasks.

package com.mido.studenttaskmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mido.studenttaskmanager.data.Task
import com.mido.studenttaskmanager.viewmodel.AppViewModelProvider
import com.mido.studenttaskmanager.viewmodel.TaskViewModel

/**
 * [TaskListScreen] is the main UI screen that displays a list of tasks.
 * It provides a [TopAppBar], a [androidx.compose.material3.FloatingActionButton] for adding new tasks,
 * and efficiently displays tasks using a [LazyColumn].
 * It also handles displaying an empty state when no tasks are available.
 *
 * @param taskList The current list of [Task] objects to be displayed, sourced from the ViewModel.
 * @param navController The [NavController] instance used to navigate to the add task screen.
 * @param taskViewModel The [TaskViewModel] instance, providing access to database operations (delete, update).
 *                      Defaults to a ViewModel provided by [AppViewModelProvider.Factory].
 */
@OptIn(ExperimentalMaterial3Api::class) // Enables the use of experimental Material 3 APIs like TopAppBar.
@Composable
fun TaskListScreen(
    taskList: List<Task>,
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Scaffold provides the basic visual structure for a Material Design screen,
    // offering slots for common UI elements like TopAppBar, FloatingActionButton, and content.
    Scaffold(
        topBar = {
            // Displays a TopAppBar at the top of the screen with the title "Home".
            TopAppBar(
                title = { Text("Home") },
                // Customizes TopAppBar colors to align with the overall theme's color scheme.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            // An ExtendedFloatingActionButton for adding new tasks.
            ExtendedFloatingActionButton(
                onClick = {
                    // Navigates to the "add_task_screen" route when the button is clicked.
                    navController.navigate("add_task_screen")
                },
                icon = { Icon(Icons.Filled.Add, "Add Task Icon") }, // Displays an Add icon.
                text = { Text(text = "Add Task") }, // Displays "Add Task" text.
                containerColor = MaterialTheme.colorScheme.primary, // Using theme's primary color for background.
                contentColor = MaterialTheme.colorScheme.onPrimary, // Using theme's onPrimary for icon and text color.
            )
        }
    ) { innerPadding ->
        // The main content area of the screen, applying padding provided by the Scaffold
        // to ensure content does not overlap with TopAppBar or FAB.
        Column(
            modifier = Modifier
                .padding(innerPadding) // Applies the padding from Scaffold to prevent overlap.
                .padding(horizontal = 8.dp), // Adds horizontal padding for better spacing.
            verticalArrangement = Arrangement.spacedBy(8.dp), // Adds vertical spacing between items for readability.
            horizontalAlignment = Alignment.Start // Aligns content to the start horizontally.
        ) {
            // Checks if the task list is empty to display an "Empty State" message.
            if (taskList.isEmpty()) {
                EmptyState(modifier = Modifier.weight(1f)) // Displays the EmptyState Composable, taking available space.
            } else {
                // LazyColumn efficiently displays a scrollable list of tasks, rendering only visible items.
                LazyColumn(modifier = Modifier.fillMaxSize()) { // Takes all available size after TopAppBar and FAB.
                    // Iterates through the task list and displays each task using TaskItem Composable.
                    items(taskList) { task ->
                        TaskItem(
                            task = task, // Passes the current task object.
                            onDeleteClick = { taskToDelete ->
                                // Calls the ViewModel's deleteTask function when a task delete is requested.
                                taskViewModel.deleteTask(taskToDelete)
                            },
                            onTaskCheckChanged = { taskToUpdate, isChecked ->
                                // Updates the completion status of the task via the ViewModel when the checkbox changes.
                                val updatedTask = taskToUpdate.copy(isCompleted = isChecked)
                                taskViewModel.updateTask(updatedTask)
                            }
                        )
                    }
                }
            }
        }
    }
}