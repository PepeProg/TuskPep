package com.example.tusk.domain.usecase

import com.example.tusk.data.repositories.TaskRepository
import javax.inject.Inject

class DeleteAllTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend fun execute() {
        taskRepository.deleteAllTasks()
    }
}