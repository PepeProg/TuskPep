package com.example.tusk.domain.entity

import java.util.*

data class TaskEntity(
    val id: UUID,
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val priority: Int,
    val description: String,
)
