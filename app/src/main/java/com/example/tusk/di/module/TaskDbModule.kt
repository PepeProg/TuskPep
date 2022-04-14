package com.example.tusk.di.module

import android.content.Context
import androidx.room.Room
import com.example.tusk.data.datastores.room.TaskDao
import com.example.tusk.data.datastores.room.TaskDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TaskDbModule {

    @Provides
    @Singleton
    fun taskDatabase(context: Context): TaskDataBase {
        return Room.databaseBuilder(
            context,
            TaskDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun taskDao(taskDataBase: TaskDataBase): TaskDao {
        return taskDataBase.taskDao()
    }

    companion object {
        private const val DATABASE_NAME = "TaskDatabase"
    }
}