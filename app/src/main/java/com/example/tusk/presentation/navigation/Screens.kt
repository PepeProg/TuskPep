package com.example.tusk.presentation.navigation

import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun AllTasks() = FragmentScreen { AllTasksFragment.newInstance() }
}