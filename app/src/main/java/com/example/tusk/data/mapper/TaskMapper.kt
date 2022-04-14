package com.example.tusk.data.mapper

import com.example.tusk.data.dto.TaskDto
import com.example.tusk.domain.entity.TaskEntity
import javax.inject.Inject

class TaskDtoMapper @Inject constructor(){

    fun mapToDto(taskEntity: TaskEntity): TaskDto {
        return TaskDto(
            title = taskEntity.title,
            startDate = taskEntity.startDate,
            endDate = taskEntity.endDate,
        )
    }

    fun mapToEntity(taskDto: TaskDto): TaskEntity {
        return TaskEntity(
            title = taskDto.title,
            startDate = taskDto.startDate,
            endDate = taskDto.endDate,
        )
    }
}