package com.example.tusk.presentation.feature.all_tasks

import com.example.tusk.domain.usecase.*
import com.example.tusk.presentation.feature.formatter.TaskFormatter
import java.util.*
import javax.inject.Inject


/**
 * Class, giving access to domain layer according to Clean-architecture
 */

class AllTasksUseCases @Inject constructor(
    private val taskFormatter: TaskFormatter,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val gelAllTasksUseCase: GelAllTasksUseCase,
    private val deleteAllTaskUseCase: DeleteAllTaskUseCase,
    private val saveTaskListUseCase: SaveTaskListUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksByDay: GetTasksByDay,
) {

    suspend fun getAllTasks(): List<TaskVo> {
        return gelAllTasksUseCase.execute().map { taskEntity ->
            taskFormatter.formatEntity(taskEntity)
        }
    }

    suspend fun getTasksByDay(date: Date): List<TaskVo> {
        return getTasksByDay.execute(date).map { taskEntity ->
            taskFormatter.formatEntity(taskEntity)
        }
    }

    suspend fun saveTask(task: TaskVo) {
        val taskEntity = taskFormatter.formatVo(task)
        saveTaskUseCase.execute(taskEntity)
    }

    suspend fun updateTasks(tasks: List<TaskVo>) {
        val taskEntities = tasks.map(taskFormatter::formatVo)
        saveTaskListUseCase.execute(taskEntities)
    }

    suspend fun deleteAllTasks() {
        deleteAllTaskUseCase.execute()
    }

    suspend fun updateTask(task: TaskVo) {
        val taskEntity = taskFormatter.formatVo(task)
        updateTaskUseCase.execute(taskEntity)
    }

    suspend fun deleteTask(task: TaskVo) {
        val taskEntity = taskFormatter.formatVo(task)
        deleteTaskUseCase.execute(taskEntity)
    }
}