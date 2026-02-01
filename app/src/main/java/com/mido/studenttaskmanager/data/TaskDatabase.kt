package com.mido.studenttaskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mido.studenttaskmanager.TaskDao // Make sure this import points to your TaskDao file

/**
 * [TaskDatabase] is the Room database class for the Student Task Manager application.
 * It serves as the main access point for the underlying SQLite database.
 *
 * It defines the entities (tables) included in the database and provides access to the DAOs
 * (Data Access Objects) which contain methods for database operations.
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    /**
     * Provides an abstract method to get the [TaskDao] instance.
     * This method is implemented by Room at compile time.
     * @return The [TaskDao] instance for performing task-related database operations.
     */
    abstract fun taskDao(): TaskDao

    /**
     * A [Companion object] to hold a singleton instance of [TaskDatabase].
     * This ensures that only one instance of the database is created,
     * preventing potential issues and resource wastage.
     */
    companion object {
        @Volatile // Ensures that changes to INSTANCE are immediately visible to all threads.
        private var INSTANCE: TaskDatabase? = null

        /**
         * Returns the singleton instance of [TaskDatabase].
         * If the instance is null, it creates a new one inside a synchronized block
         * to prevent multiple threads from creating multiple database instances.
         *
         * @param context The application context, used for database creation.
         * @return The singleton [TaskDatabase] instance.
         */
        fun getDatabase(context: Context): TaskDatabase {
            // If INSTANCE is not null, then return it.
            // If it is null, then create a new instance.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Use applicationContext to prevent memory leaks.
                    TaskDatabase::class.java,    // The database class.
                    "task_database"              // The name of the database file.
                )
                    .build() // Builds the database instance.
                INSTANCE = instance // Assigns the newly created instance to INSTANCE.
                instance // Returns the instance.
            }
        }
    }
}