package com.example.tusk.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun getCicerone() = Cicerone.create()

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router
}