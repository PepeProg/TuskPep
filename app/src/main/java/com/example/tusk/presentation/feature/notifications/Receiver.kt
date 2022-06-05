package com.example.tusk.presentation.feature.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tusk.R
import com.example.tusk.presentation.feature.all_tasks.CHANNEL_ID

class Receiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // We use this to make sure that we execute code, only when this exact
        // Alarm triggered our Broadcast receiver
        if (intent?.action == "MyBroadcastReceiverAction") {
            Log.d("ALARM", "RECEIVED")
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.sun)
            .setContentTitle("Hurry up!")
            .setContentText("You have a task, which deadline is now!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(228, builder.build())
        }
    }
}