package com.example.tusk.data.repositories

import com.example.tusk.data.datastores.TaskDbDataStore
import com.example.tusk.data.mapper.TaskDtoMapper
import com.example.tusk.domain.entity.TaskEntity
import java.util.*
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val taskDbDataStore: TaskDbDataStore,
    private val taskDtoMapper: TaskDtoMapper
) {

    suspend fun addTask(task: TaskEntity) {
        val taskDto = taskDtoMapper.mapToDto(task)

        return taskDbDataStore.addTask(taskDto)
    }

    suspend fun getAllTasks(): List<TaskEntity> {
        return taskDbDataStore.getAllTasks().map { taskDto ->
            taskDtoMapper.mapToEntity(taskDto)
        }
    }

    suspend fun getTasksByDay(date: Date): List<TaskEntity> {
        val entities = getAllTasks()
        val currCalendar = Calendar.getInstance().apply { time = date }
        val buffCalendar = Calendar.getInstance()

        val filteredEntities = entities.filter { entity ->
            buffCalendar.time = entity.endDate
            val year = currCalendar.get(Calendar.YEAR) == buffCalendar.get(Calendar.YEAR)
            val month = currCalendar.get(Calendar.MONTH) == buffCalendar.get(Calendar.MONTH)
            val day = currCalendar.get(Calendar.DAY_OF_MONTH) == buffCalendar.get(Calendar.DAY_OF_MONTH)
            year && month && day
        }

        return filteredEntities
    }

    suspend fun deleteAllTasks() {
        taskDbDataStore.deleteAllTasks()
    }

    suspend fun deleteTask(task: TaskEntity) {
        val taskDto = taskDtoMapper.mapToDto(task)

        taskDbDataStore.deleteTask(taskDto)
    }

    suspend fun updateTasks(tasks: List<TaskEntity>) {
        val taskDtos = tasks.map(taskDtoMapper::mapToDto)

        taskDbDataStore.updateTasks(taskDtos)
    }

    suspend fun updateTask(task: TaskEntity) {
        val taskDto = taskDtoMapper.mapToDto(task)

        taskDbDataStore.updateTask(taskDto)
    }
}