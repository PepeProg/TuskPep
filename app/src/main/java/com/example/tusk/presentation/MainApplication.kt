package com.example.tusk.presentation

import android.app.Application
import com.example.tusk.di.component.AppComponent
import com.example.tusk.di.component.DaggerAppComponent

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        dagger = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }

    companion object {
        lateinit var dagger: AppComponent
    }
}