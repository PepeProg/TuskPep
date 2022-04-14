package com.example.tusk.presentation.feature.all_tasks

import com.example.tusk.domain.usecase.SaveTaskUseCase
import com.example.tusk.domain.usecase.GelAllTasksUseCase
import com.example.tusk.presentation.feature.formatter.TaskFormatter
import javax.inject.Inject

class AllTasksUseCases @Inject constructor(
    private val taskFormatter: TaskFormatter,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val gelAllTasksUseCase: GelAllTasksUseCase,
) {

    suspend fun getAllTasks(): List<TaskVo> {
        return gelAllTasksUseCase.execute().map { taskEntity ->
            taskFormatter.formatEntity(taskEntity)
        }
    }

    suspend fun saveTask(task: TaskVo) {
        val taskEntity = taskFormatter.formatVo(task)
        saveTaskUseCase.execute(taskEntity)
    }
}