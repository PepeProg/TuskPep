package com.example.tusk.presentation.feature.week_days

import java.util.*

data class WeekDayVo(
    val name: String,
    val order_id: Long,
    val weatherType: Int,
    val degree: Int,
    val date: Date,
)