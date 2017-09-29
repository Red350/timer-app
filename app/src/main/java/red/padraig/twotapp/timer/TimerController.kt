package red.padraig.twotapp.timer

import android.content.Context
import android.os.CountDownTimer
import android.widget.NumberPicker
import io.reactivex.Flowable
import io.reactivex.functions.Function3
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import red.padraig.twotapp.R
import red.padraig.twotapp.alarm.AlarmSetter
import red.padraig.twotapp.ui.activities.MainActivity

/**
 * Created by Red on 26/09/2017.
 */
class TimerController(private val context: Context) {

    // TODO: Refactor this class to not take views as parameters

    private val TICK_INTERVAL = 10L

    private val timerModel: TimerModel = TimerModel(context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE) )
    private val alarmSetter = AlarmSetter.Impl()
    private val hoursPicker: NumberPicker = (context as MainActivity).numberpicker_main_hours
    private val minutesPicker: NumberPicker = (context as MainActivity).numberpicker_main_minutes
    private val secondsPicker: NumberPicker = (context as MainActivity).numberpicker_main_seconds
    private var timer: CountDownTimer? = null
    private var millisRemainingOnTimerPaused: Long = 0

    var timerTick: PublishSubject<Long> = PublishSubject.create()

    init {
        timerModel.hoursChanged.subscribe { hoursPicker.value = it }
        timerModel.minutesChanged.subscribe { minutesPicker.value = it}
        timerModel.secondsChanged.subscribe { secondsPicker.value = it}

        hoursPicker.setOnValueChangedListener { _, _, hours -> timerModel.hours = hours }
        minutesPicker.setOnValueChangedListener { _, _, minutes -> timerModel.minutes = minutes }
        secondsPicker.setOnValueChangedListener { _, _, seconds -> timerModel.seconds = seconds }
    }

    fun startTimer() {
        timer = createTimer(timerModel.millis).start()
    }

    fun pauseTimer() {
        timer?.cancel()
    }

    fun resumeTimer() {
        timer = createTimer(millisRemainingOnTimerPaused).start()
    }

    fun resetTimer() {
        timer?.cancel()
    }

    fun restore() {
        timerModel.restore()
    }

    fun save() {
        timerModel.save()
    }

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

    private fun createTimer(millis: Long): CountDownTimer {
        // Register broadcast with OS
        alarmSetter.set(context, System.currentTimeMillis() + millis)

        return object : CountDownTimer(millis, TICK_INTERVAL) {
            override fun onFinish() {
                timerTick.onNext(-1)
            }

            override fun onTick(millisRemaining: Long) {
                timerTick.onNext(millisRemaining)
                millisRemainingOnTimerPaused = millisRemaining
            }
        }
    }
}
