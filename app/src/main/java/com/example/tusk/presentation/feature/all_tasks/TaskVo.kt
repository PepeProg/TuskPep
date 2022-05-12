package com.example.tusk.presentation.feature.all_tasks

import java.util.*

data class TaskVo(
    val id: UUID,
    var title: String,
    val startDate: Date,
    var endDate: Date,
    var priority: Int,
)