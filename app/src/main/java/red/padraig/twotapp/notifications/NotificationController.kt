package red.padraig.twotapp.notifications

import android.app.NotificationManager
import android.content.Context

/**
 * Created by Red on 05/10/2017.
 */
interface NotificationController {

    fun update(id: Int, icon: Int, title: String, message: String)

    fun cancel(id: Int)

    class Impl(context: Context): NotificationController {

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val timerNotificationFactory = TimerNotificationFactory(context)

        override fun update(id: Int, icon: Int, title: String, message: String) {
            notificationManager.notify(id, timerNotificationFactory.create(icon, title, message))
        }

        override fun cancel(id: Int) {
            notificationManager.cancel(id)
        }

    }
}