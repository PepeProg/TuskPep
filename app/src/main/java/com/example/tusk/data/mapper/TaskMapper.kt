package com.example.tusk.data.mapper

import com.example.tusk.data.dto.TaskDto
import com.example.tusk.domain.entity.TaskEntity
import javax.inject.Inject

class TaskDtoMapper @Inject constructor(){

    fun mapToDto(taskEntity: TaskEntity): TaskDto {
        return TaskDto(
            id = taskEntity.id,
            title = taskEntity.title,
            startDate = taskEntity.startDate,
            endDate = taskEntity.endDate,
            priority = taskEntity.priority,
            description = taskEntity.description,
        )
    }

    fun mapToEntity(taskDto: TaskDto): TaskEntity {
        return TaskEntity(
            id = taskDto.id,
            title = taskDto.title,
            startDate = taskDto.startDate,
            endDate = taskDto.endDate,
            priority = taskDto.priority,
            description = taskDto.description,
        )
    }
}