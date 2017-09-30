package red.padraig.twotapp.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent


/**
 * Created by Red on 28/09/2017.
 */
interface AlarmBroadcastSetter {

    fun set(context: Context, triggerAtMillis: Long)

    fun cancel(context: Context)

    class Impl: AlarmBroadcastSetter {

        override fun set(context: Context, triggerAtMillis: Long) {
            getAlarmManager(context).setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, createPendingIntent(context))
        }

        override fun cancel(context: Context) {
            getAlarmManager(context).cancel(createPendingIntent(context))
        }

        private fun createPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, AlarmBroadcastReceiver::class.java)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }

        private fun getAlarmManager(context: Context): AlarmManager {
            return context.getSystemService(AlarmManager::class.java)
        }

    }
}