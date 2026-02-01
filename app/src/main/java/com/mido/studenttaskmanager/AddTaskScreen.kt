// AddTaskScreen.kt - Screen for adding new tasks

package com.mido.studenttaskmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Corrected import for back arrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mido.studenttaskmanager.data.Task
import com.mido.studenttaskmanager.viewmodel.AppViewModelProvider
import com.mido.studenttaskmanager.viewmodel.TaskViewModel

/**
 * [AddTaskScreen] is dedicated to adding a new task.
 * It provides input fields for the title and description, and a button to add the task.
 * The screen includes a [TopAppBar] with a back button for navigation.
 *
 * @param navController The [NavController] instance to manage navigation (e.g., navigating back).
 * @param taskViewModel The [TaskViewModel] instance, used to save the new task to the database.
 *                      Defaults to a ViewModel provided by [AppViewModelProvider.Factory].
 */
@OptIn(ExperimentalMaterial3Api::class) // Enables the use of experimental Material 3 APIs like TopAppBar.
@Composable
fun AddTaskScreen(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Scaffold provides a basic visual structure for a Material Design screen, including a TopAppBar.
    Scaffold(
        topBar = {
            // Displays a TopAppBar at the top of the screen with the title "Add New Task" and a back button.
            TopAppBar(
                title = { Text("Add New Task") },
                navigationIcon = {
                    // Back button that pops the current screen from the navigation stack,
                    // returning to the previous screen.
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                // Customizes TopAppBar colors to align with the overall theme's color scheme.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // The main column containing the input fields and the add button.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Applies the padding provided by Scaffold to prevent overlap.
        ) {
            // States to manage input field values and the error state for the title (if it's empty).
            var title by remember { mutableStateOf("") }
            var description by remember { mutableStateOf("") }
            var showTitleError by remember { mutableStateOf(false) }

            // The [AddTaskSection] Composable, which contains the input fields and add button.
            // This is where the State Hoisting principle is applied.
            AddTaskSection(
                title = title,
                description = description,
                showTitleError = showTitleError, // Passes the error state for the title field.
                onTitleChange = { newTitle ->
                    title = newTitle // Updates the title value.
                    showTitleError = false // Hides the error message when typing begins.
                },
                onDescriptionChange = { newDesc -> description = newDesc }, // Updates the description value.
                onAddTaskClick = {
                    // When the add button is clicked, input data is validated.
                    if (title.isNotBlank()) { // Checks that the title field is not empty.
                        // Adds the new task to the database via the ViewModel.
                        // The description can be empty here.
                        taskViewModel.insertTask(Task(title = title, description = description))
                        // Navigates back to the previous screen after successfully adding the task.
                        navController.popBackStack()
                    } else {
                        // If the title is empty, the error message is displayed.
                        showTitleError = true
                    }
                }
            )
        }
    }
}