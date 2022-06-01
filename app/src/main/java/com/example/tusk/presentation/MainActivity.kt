package com.example.tusk.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tusk.R
import com.example.tusk.presentation.feature.all_tasks.CHANNEL_ID
import com.example.tusk.presentation.navigation.MainNavigator
import com.example.tusk.presentation.navigation.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val navigator = MainNavigator(this, R.id.fragment_container)

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            Log.d("KRUGLIK", "PEPE")
            val name = "Kruglik"
            val descriptionText = "Vlad (Stas)"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            Log.d("KRUGLIK", "PUPU")
        }

        MainApplication.dagger.inject(this)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
        initiateNavigation()
    }

    override fun onPause() {
        super.onPause()

        navigatorHolder.removeNavigator()
    }

    private fun initiateNavigation() {
        router.replaceScreen(Screens.AllTasks())
    }
}