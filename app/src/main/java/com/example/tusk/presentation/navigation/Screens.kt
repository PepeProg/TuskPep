package com.example.tusk.presentation.navigation

import android.app.Notification
import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import com.example.tusk.presentation.feature.notifications.NotificationsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun AllTasks() = FragmentScreen { AllTasksFragment.newInstance() }
    fun NotificationsScreen() = FragmentScreen { NotificationsFragment.newInstance() }
}