package com.example.tusk.data.datastores

import com.example.tusk.data.datastores.room.TaskDao
import com.example.tusk.data.dto.TaskDto
import java.util.*
import javax.inject.Inject

/**
 * Api to database
 */
class TaskDbDataStore @Inject constructor(
    private val taskDao: TaskDao,
) {

    suspend fun addTask(task: TaskDto) {
        taskDao.addTasks(listOf(task))
    }

    suspend fun addTasks(tasks: List<TaskDto>) {
        taskDao.addTasks(tasks)
    }

    suspend fun updateTasks(tasks: List<TaskDto>) {
        taskDao.updateTasks(tasks)
    }

    suspend fun getAllTasks(): List<TaskDto> {
        return taskDao.getAllTasks()
    }

    suspend fun getTaskById(id: UUID): TaskDto {
        return taskDao.getTaskById(id)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    suspend fun updateTask(task: TaskDto) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskDto) {
        taskDao.deleteTask(task)
    }
}