package com.example.tusk.data.datastores.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tusk.data.dto.TaskDto

/**
 * Room database, later will be generated to Database class, which we are using in DI
 */

@Database(entities = [TaskDto::class], version = 1)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao() : TaskDao
}