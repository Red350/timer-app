package red.padraig.twotapp.timer

import android.content.SharedPreferences
import io.reactivex.processors.BehaviorProcessor

/**
 * Created by Red on 21/09/2017.
 */
class TimerModel(val sharedPreferences: SharedPreferences) {

    val HOURS = "HOURS"
    val MINUTES = "MINUTES"
    val SECONDS = "SECONDS"

    var hours = 0
        set(value) {
            if (value != hours) {
                field = value
                hoursChanged.onNext(value)
            }
        }
    var minutes = 0
        set(value) {
            if (value != minutes) {
                field = value
                minutesChanged.onNext(value)
            }
        }
    var seconds = 0
        set(value) {
            if (value != seconds) {
                field = value
                secondsChanged.onNext(value)
            }
        }

    val hoursChanged: BehaviorProcessor<Int> = BehaviorProcessor.createDefault(hours)
    val minutesChanged: BehaviorProcessor<Int> = BehaviorProcessor.createDefault(minutes)
    val secondsChanged: BehaviorProcessor<Int> = BehaviorProcessor.createDefault(seconds)
    val millis: Long
        get() {
            return ((hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + seconds * 1000).toLong()
        }

    fun restore() {
        val storedHours = sharedPreferences.getInt(HOURS, -1)
        val storedMinutes = sharedPreferences.getInt(MINUTES, -1)
        val storedSeconds = sharedPreferences.getInt(SECONDS, -1)

        if (storedHours != -1)
            hours = storedHours
        if (storedMinutes != -1)
            minutes = storedMinutes
        if (storedSeconds != -1)
            seconds = storedSeconds
    }

    fun save() {
        val editor = sharedPreferences.edit()
        editor.putInt(HOURS, hours)
        editor.putInt(MINUTES, minutes)
        editor.putInt(SECONDS, seconds)
        editor.apply()
    }
}