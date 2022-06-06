package com.example.tusk.di.component

import android.content.Context
import com.example.tusk.di.module.NavigationModule
import com.example.tusk.di.module.TaskDbModule
import com.example.tusk.presentation.MainActivity
import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import com.example.tusk.presentation.feature.notifications.NotificationsFragment
import com.example.tusk.presentation.feature.week_days.WeekDaysFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * The main component. containing all functions, that allow us to inject dependencies
 */

@Singleton
@Component(modules = [
    NavigationModule::class,
    TaskDbModule::class,
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(allTasksFragment: AllTasksFragment)
    fun inject(weekDaysFragment: WeekDaysFragment)
    fun inject(notificationsFragment: NotificationsFragment) {

    }


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}