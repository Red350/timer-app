package red.padraig.twotapp.timer

import android.content.SharedPreferences
import io.reactivex.processors.BehaviorProcessor

/**
 * Created by Red on 21/09/2017.
 */
class TimerModel(val sharedPreferences: SharedPreferences) {

    val HOUR = "HOUR"
    val MINUTE = "MINUTE"

    var hour = 0
        set(value) {
            if (value != hour && isInRange(value)) {
                field = value
                hourChanged.onNext(value)
            }
        }
    var minute = 0
        set(value) {
            if (value != minute && isInRange(value)) {
                field = value
                minuteChanged.onNext(value)
            }
        }

    val hourChanged: BehaviorProcessor<Int> = BehaviorProcessor.createDefault(hour)
    val minuteChanged: BehaviorProcessor<Int> = BehaviorProcessor.createDefault(minute)
    val millis: Long
        get() {
            return ((hour * 60 * 60 * 1000) + minute * 60 * 1000).toLong()
        }

    private fun isInRange(value: Int): Boolean {
        return value in 0..99
    }

    fun restore() {
        val storedHour = sharedPreferences.getInt(HOUR, -1)
        val storedMinute = sharedPreferences.getInt(MINUTE, -1)

        if (storedHour != -1)
            hour = storedHour

        if (storedMinute != -1)
            minute = storedMinute
    }

    fun save() {
        val editor = sharedPreferences.edit()
        editor.putInt(HOUR, hour)
        editor.putInt(MINUTE, minute)
        editor.commit()
    }
}