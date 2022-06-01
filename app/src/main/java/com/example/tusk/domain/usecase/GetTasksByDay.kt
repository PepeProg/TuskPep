package com.example.tusk.domain.usecase

import com.example.tusk.data.repositories.TaskRepository
import com.example.tusk.domain.entity.TaskEntity
import java.util.*
import javax.inject.Inject

class GetTasksByDay @Inject constructor(
    private val taskRepository: TaskRepository,
){
    suspend fun execute(date: Date): List<TaskEntity> {
        return taskRepository.getTasksByDay(date)
    }
}