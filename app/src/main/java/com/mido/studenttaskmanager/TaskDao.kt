package com.mido.studenttaskmanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mido.studenttaskmanager.data.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: Task)

    /**
     * Deletes an existing [Task] from the database.
     * The task is identified by its primary key.
     * This is a suspend function, meaning it should be called from a coroutine.
     *
     * @param task The [Task] object to be deleted.
     */
    @Delete
    suspend fun deleteTask(task: Task)

    /**
     * Updates an existing [Task] in the database.
     * The task is identified by its primary key.
     * This is a suspend function, meaning it should be called from a coroutine.
     *
     * @param task The [Task] object to be updated.
     */
    @Update
    suspend fun updateTask(task: Task)

    /**
     * Retrieves all tasks from the `tasks_table`, ordered by their ID in ascending order.
     * The result is returned as a [Flow] of a list of [Task] objects,
     * which allows for observing changes to the database in real-time.
     *
     * @return A [Flow] emitting a [List] of all [Task]s.
     */
    @Query("SELECT * FROM tasks_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<Task>>
}