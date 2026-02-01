package com.mido.studenttaskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mido.studenttaskmanager.data.Task
import com.mido.studenttaskmanager.TaskDao // Ensure correct import for TaskDao
import com.mido.studenttaskmanager.data.TaskDatabase // Ensure correct import for TaskDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

/**
 * [TaskViewModel] acts as an intermediary between the UI (screens) and the data layer (database).
 * It provides UI-related data and handles business logic related to tasks.
 * Data exposed by the ViewModel survives configuration changes (like screen rotations).
 *
 * @param taskDao The [TaskDao] instance to interact with the [TaskDatabase].
 */
class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    /**
     * A [Flow] of a [List] of all [Task]s from the database, ordered by ID.
     * The UI collects this Flow to observe real-time updates.
     */
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    /**
     * Inserts a new [Task] into the database.
     * This operation is launched in a coroutine within the [viewModelScope] to perform work off the main thread.
     *
     * @param task The [Task] object to be inserted.
     */
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    /**
     * Deletes an existing [Task] from the database.
     * This operation is launched in a coroutine within the [viewModelScope].
     *
     * @param task The [Task] object to be deleted.
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    /**
     * Updates an existing [Task] in the database.
     * This operation is launched in a coroutine within the [viewModelScope].
     *
     * @param task The [Task] object to be updated.
     */
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }
}

/**
 * Provides a [ViewModelProvider.Factory] to instantiate [TaskViewModel].
 * This factory ensures that [TaskViewModel] receives the necessary [TaskDao]
 * by retrieving the application context from [CreationExtras] to access the [TaskDatabase].
 */
object AppViewModelProvider {
    val Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            // Checks if the requested ViewModel class is assignable from TaskViewModel.
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                // Retrieves the application context from the CreationExtras.
                val context = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]).applicationContext
                // Obtains an instance of TaskDao from the singleton TaskDatabase.
                val taskDao = TaskDatabase.getDatabase(context).taskDao()
                // Suppresses the unchecked cast warning as we are sure of the type here.
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(taskDao) as T
            }
            // Throws an exception if an unknown ViewModel class is requested.
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}