package red.padraig.twotapp.timer

import android.content.Context
import android.os.CountDownTimer
import android.widget.NumberPicker
import io.reactivex.Flowable
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import red.padraig.twotapp.R
import red.padraig.twotapp.alarm.AlarmBroadcastSetter
import red.padraig.twotapp.ui.activities.MainActivity

/**
 * Created by Red on 26/09/2017.
 */
class TimerController(private val context: Context) {

    private val TICK_INTERVAL = 10L

    private val timerModel: TimerModel = TimerModel(context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE) )
    private val alarmSetter = AlarmBroadcastSetter.Impl()

    private val hoursPicker: NumberPicker = (context as MainActivity).numberpicker_main_hours
    private val minutesPicker: NumberPicker = (context as MainActivity).numberpicker_main_minutes
    private val secondsPicker: NumberPicker = (context as MainActivity).numberpicker_main_seconds

    private var timer: CountDownTimer? = null
    private var millisRemaining: Long = -1 // -1 when no timer active

    var timerTick: BehaviorSubject<Long> = BehaviorSubject.create()

    val timerActive: Boolean
        get() = (timerModel.getActiveTimerDueTime() != -1L)

    val timerPaused: Boolean
        get() = (timerModel.getPausedTimer() != -1L)

    init {
        timerModel.hoursChanged.subscribe { hoursPicker.value = it }
        timerModel.minutesChanged.subscribe { minutesPicker.value = it}
        timerModel.secondsChanged.subscribe { secondsPicker.value = it}

        hoursPicker.setOnValueChangedListener { _, _, hours -> timerModel.hours = hours }
        minutesPicker.setOnValueChangedListener { _, _, minutes -> timerModel.minutes = minutes }
        secondsPicker.setOnValueChangedListener { _, _, seconds -> timerModel.seconds = seconds }
    }

    fun startTimer() {
        val millis: Long
        if (timerActive) {
            millis = timerModel.getActiveTimerDueTime() - System.currentTimeMillis()
        } else if (timerPaused) {
            millis = timerModel.getPausedTimer()
            millisRemaining = millis
            timerTick.onNext(millis)
        } else {
            millis = timerModel.millis
        }
        timer = createTimer(millis).start()
    }

    fun pauseTimer() {
        cancelTimer()
        timerModel.savePausedTimer(millisRemaining)
    }

    fun resumeTimer() {
        timer = createTimer(millisRemaining).start()
        timerModel.clearPausedTimer()
    }

    fun resetTimer() {
        cancelTimer()
        timerModel.clearPausedTimer()
    }

    fun restoreSettings() = timerModel.restoreSettings()

    fun saveSettings() = timerModel.saveSettings()

    fun createEnableStartButtonFlowable(): Flowable<Boolean> {
        return Flowable.combineLatest(
                timerModel.hoursChanged,
                timerModel.minutesChanged,
                timerModel.secondsChanged,
                Function3<Int, Int, Int, Boolean> { hours, minutes, seconds ->
                    !((hours == 0) && (minutes == 0) && (seconds == 0))
                }
        )
    }

    private fun createTimer(timerMillis: Long): CountDownTimer {
        val dueTime = System.currentTimeMillis() + timerMillis
        // Register broadcast with OS
        alarmSetter.set(context, dueTime)
        // Store timer in persistent storage
        timerModel.saveActiveTimerDueTime(dueTime)

        return object : CountDownTimer(timerMillis, TICK_INTERVAL) {

            override fun onFinish() {
                timerTick.onNext(0)
                millisRemaining = -1
            }

            override fun onTick(millisRemaining: Long) {
                timerTick.onNext(millisRemaining)
                this@TimerController.millisRemaining = millisRemaining
            }
        }
    }

    private fun cancelTimer() {
        alarmSetter.cancel(context)
        timer?.cancel()
        timerModel.clearActiveTimerDueTime()
    }
}
