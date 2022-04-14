package com.example.tusk.domain.usecase

import com.example.tusk.data.repositories.TaskRepository
import com.example.tusk.domain.entity.TaskEntity
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {

    suspend fun execute(task: TaskEntity) {
        taskRepository.addTask(task)
    }
}