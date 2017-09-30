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
    val ACTIVE_TIMER = "ACTIVE_TIMER"
    val PAUSED_TIMER = "PAUSED_TIMER"

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

    fun restoreSettings() {
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

    fun saveSettings() {
        val editor = sharedPreferences.edit()
        editor.putInt(HOURS, hours)
        editor.putInt(MINUTES, minutes)
        editor.putInt(SECONDS, seconds)
        editor.apply()
    }

    fun saveActiveTimerDueTime(millis: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(ACTIVE_TIMER, millis)
        editor.apply()
    }

    fun getActiveTimerDueTime(): Long {
        return sharedPreferences.getLong(ACTIVE_TIMER, -1)
    }

    fun clearActiveTimerDueTime() {
        saveActiveTimerDueTime(-1)
    }

    fun savePausedTimer(millis: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(PAUSED_TIMER, millis)
        editor.apply()
    }

    fun getPausedTimer(): Long {
        return sharedPreferences.getLong(PAUSED_TIMER, -1)
    }

    fun clearPausedTimer() {
        savePausedTimer(-1)
    }
}