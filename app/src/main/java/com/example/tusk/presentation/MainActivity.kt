package com.example.tusk.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.tusk.R
import com.example.tusk.presentation.navigation.MainNavigator
import com.example.tusk.presentation.navigation.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : FragmentActivity() {

    private val navigator = MainNavigator(this, R.id.fragment_container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainApplication.dagger.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
        router.navigateTo(Screens.AllTasks())
    }

    override fun onPause() {
        super.onPause()

        navigatorHolder.removeNavigator()
    }
}