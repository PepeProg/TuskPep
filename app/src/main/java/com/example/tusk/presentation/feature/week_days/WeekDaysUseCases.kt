package com.example.tusk.presentation.feature.week_days

import java.util.*
import javax.inject.Inject

class WeekDaysUseCases @Inject constructor() {

    suspend fun getAllDays(date: Date): List<WeekDayVo> {
        val listDays = mutableListOf<WeekDayVo>()
        val weekList = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
            "Sunday")

        val currCalendar = Calendar.getInstance().apply { time = date }
        val dayOfWeek = currCalendar.get(Calendar.DAY_OF_WEEK)
        val dayOfMonth = currCalendar.get(Calendar.DAY_OF_MONTH)

        currCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth - dayOfWeek + 1)


        for (i in 1..7) {
            currCalendar.set(Calendar.DAY_OF_MONTH, currCalendar.get(Calendar.DAY_OF_MONTH) + 1)
            listDays.add(
                WeekDayVo(
                    name = weekList[i - 1],
                    order_id = i.toLong(),
                    weatherType = 0,
                    degree = 0,
                    currCalendar.time
                )
            )
        }

        return listDays
    }

}