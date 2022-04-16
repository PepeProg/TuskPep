package com.example.tusk.presentation.feature.all_tasks

import java.util.*

data class TaskVo(
    val id: UUID,
    val title: String,
    val startDate: Date,
    val endDate: Date,
)