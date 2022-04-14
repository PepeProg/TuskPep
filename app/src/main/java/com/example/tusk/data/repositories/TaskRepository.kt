package com.example.tusk.data.repositories

import com.example.tusk.data.datastores.TaskDbDataStore
import com.example.tusk.data.mapper.TaskDtoMapper
import com.example.tusk.domain.entity.TaskEntity
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
}