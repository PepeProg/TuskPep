package com.example.tusk.di.component

import com.example.tusk.di.module.NavigationModule
import com.example.tusk.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NavigationModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
}