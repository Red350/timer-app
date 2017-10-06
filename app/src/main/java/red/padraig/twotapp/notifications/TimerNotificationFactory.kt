package red.padraig.twotapp.notifications

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import red.padraig.twotapp.ui.activities.MainActivity

/**
 * Created by Red on 03/10/2017.
 */
class TimerNotificationFactory(val context: Context) {

    fun create(icon: Int, title: String, message: String): Notification {
        return Notification.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(createPendingIntent(context))
                .setOngoing(true)
                .build()
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

}