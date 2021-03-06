package com.example.tusk.presentation.navigation

import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import com.example.tusk.presentation.feature.notifications.NotificationsFragment
import com.example.tusk.presentation.feature.week_days.WeekDaysFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen
import java.util.*

object Screens {
    fun AllTasks(date: Date) = FragmentScreen { AllTasksFragment.newInstance(date) }
    fun NotificationsScreen() = FragmentScreen { NotificationsFragment.newInstance() }
    fun WeekDaysScreen() = FragmentScreen { WeekDaysFragment.newInstance() }
}