package com.example.tusk.di.component

import android.content.Context
import com.example.tusk.di.module.NavigationModule
import com.example.tusk.di.module.TaskDbModule
import com.example.tusk.presentation.MainActivity
import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NavigationModule::class,
    TaskDbModule::class,
])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(allTasksFragment: AllTasksFragment)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}