package red.padraig.twotapp.alarm

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

/**
 * Created by Red on 28/09/2017.
 */
interface TimerAlarm {

    fun play()

    fun stop()

    class Impl(context: Context): TimerAlarm {

        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone: Ringtone = RingtoneManager.getRingtone(context, notification)

        private var played = false

        override fun play() {
            if (!played) {
                ringtone.play()
                played = true
            } else {
                throw RuntimeException("Ringtone already played")
            }
        }

        override fun stop() {
            ringtone.stop()
        }

    }
}