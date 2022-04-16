package com.example.tusk.presentation.feature.formatter

import com.example.tusk.domain.entity.TaskEntity
import com.example.tusk.presentation.feature.all_tasks.TaskVo
import javax.inject.Inject

class TaskFormatter @Inject constructor() {

    fun formatEntity(taskEntity: TaskEntity): TaskVo {
        return TaskVo(
            id = taskEntity.id,
            title = taskEntity.title,
            startDate = taskEntity.startDate,
            endDate = taskEntity.endDate,
        )
    }

    fun formatVo(taskVo: TaskVo): TaskEntity {
        return TaskEntity(
            id = taskVo.id,
            title = taskVo.title,
            startDate = taskVo.startDate,
            endDate = taskVo.endDate,
        )
    }
}