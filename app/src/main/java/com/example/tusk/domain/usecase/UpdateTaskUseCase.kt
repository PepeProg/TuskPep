package com.example.tusk.domain.usecase

import com.example.tusk.data.repositories.TaskRepository
import com.example.tusk.domain.entity.TaskEntity
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
){
    suspend fun execute(task: TaskEntity) {
        repository.updateTask(task)
    }
}