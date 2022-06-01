package com.example.tusk.presentation.feature.week_days

import javax.inject.Inject

class WeekDaysUseCases @Inject constructor() {

    fun getAllDays(): List<WeekDayVo> {
        val listDays = mutableListOf<WeekDayVo>()
        val weekList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
        "Sunday")
        for (i in 1..7) {
            listDays.add(
                WeekDayVo(
                name = weekList[i - 1],
                order_id = i.toLong(),
                weatherType = 0,
                degree = 0,
            )
            )
        }

        return listDays
    }

}