package red.padraig.twotapp.alarm

import android.content.Context
import android.media.AudioAttributes
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

/**
 * Created by Red on 28/09/2017.
 */
interface TimerAlarmAnnunciator {

    fun play()

    fun stop()

    class Impl(context: Context): TimerAlarmAnnunciator {

        private val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        private val ringtone: Ringtone = RingtoneManager.getRingtone(context, notification)
        private var played = false

        init {
            ringtone.audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
        }

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