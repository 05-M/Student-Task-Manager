package com.mido.studenttaskmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mido.studenttaskmanager.data.Task // Import the Task data class

/**
 * [TaskItem] is a Composable that displays a single task item.
 * It includes a checkbox for completion status, the task title and description,
 * and a delete button.
 *
 * @param task The [Task] object to be displayed.
 * @param onDeleteClick Lambda function invoked when the delete button is clicked.
 *                      Receives the [Task] to be deleted.
 * @param onTaskCheckChanged Lambda function invoked when the checkbox state changes.
 *                           Receives the [Task] and its new completion status.
 */
@OptIn(ExperimentalMaterial3Api::class) // Opt-in for experimental Material 3 APIs if used within.
@Composable
fun TaskItem(
    task: Task,
    onDeleteClick: (Task) -> Unit,
    onTaskCheckChanged: (Task, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Makes the card fill the width of its parent.
            .padding(vertical = 4.dp, horizontal = 8.dp), // Adds vertical and horizontal padding around the card.
        shape = MaterialTheme.shapes.medium // Applies medium rounded corners to the card.
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Adds internal padding within the card.
            verticalAlignment = Alignment.CenterVertically, // Vertically centers items in the row.
            horizontalArrangement = Arrangement.SpaceBetween // Distributes items with space between them.
        ) {
            Checkbox(
                checked = task.isCompleted, // Binds checkbox state to task's completion status.
                onCheckedChange = { isChecked ->
                    onTaskCheckChanged(task, isChecked) // Notifies parent when checkbox state changes.
                },
                modifier = Modifier.padding(end = 8.dp) // Adds padding to the right of the checkbox.
            )
            Column(modifier = Modifier.weight(1f)) { // Column for title and description, taking available space.
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        // Applies strikethrough text decoration if the task is completed.
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                    )
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // Uses a muted color from the theme.
                )
            }
            IconButton(onClick = { onDeleteClick(task) }) { // Button to trigger task deletion.
                Icon(
                    imageVector = Icons.Filled.Delete, // Delete icon.
                    contentDescription = "Delete Task",
                    tint = MaterialTheme.colorScheme.error // Uses theme's error color (usually red) for the icon.
                )
            }
        }
    }
}

/**
 * [AddTaskSection] is a Composable that provides input fields for adding a new task.
 * It follows the State Hoisting pattern, receiving its state and callbacks from a parent Composable.
 *
 * @param title The current text in the title input field.
 * @param description The current text in the description input field.
 * @param onTitleChange Callback invoked when the title text changes.
 * @param onDescriptionChange Callback invoked when the description text changes.
 * @param onAddTaskClick Callback invoked when the add task button is clicked.
 * @param showTitleError Boolean indicating whether to display an error for the title field.
 */
@Composable
fun AddTaskSection(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddTaskClick: () -> Unit,
    showTitleError: Boolean
){
    Column(modifier = Modifier.padding(16.dp)){ // Column containing the input fields and button, with internal padding.
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = { Text("Task Title *") }, // Label for the title input field, indicating it's mandatory.
            isError = showTitleError, // Displays error state if showTitleError is true.
            supportingText = {
                if (showTitleError) {
                    Text("Task title cannot be empty") // Error message for empty title.
                }
            },
            modifier = Modifier.fillMaxWidth() // Makes the text field fill the width.
        )
        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = { Text("Task Description (Optional)") }, // Label for the optional description field.
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { onAddTaskClick() } , // Triggers the onAddTaskClick callback.
            modifier = Modifier.padding(top = 8.dp) // Adds padding above the button.
        ) {
            Text("Add Task")
        }
    }
}

/**
 * [EmptyState] is a Composable that displays a friendly message and icon
 * when a list (e.g., the task list) is empty.
 *
 * @param modifier The modifier to be applied to this Composable.
 */
@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), // Makes the column fill all available space.
        verticalArrangement = Arrangement.Center, // Vertically centers its content.
        horizontalAlignment = Alignment.CenterHorizontally // Horizontally centers its content.
    ) {
        Icon(
            imageVector = Icons.Filled.Info, // Information icon.
            contentDescription = "No tasks",
            modifier = Modifier.padding(bottom = 8.dp), // Adds padding below the icon.
            tint = MaterialTheme.colorScheme.onSurfaceVariant // Uses a muted color from the theme.
        )
        Text(
            text = "No tasks yet...\nAdd your first one ✨",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 24.sp // Sets line height for better readability.
        )
    }
}