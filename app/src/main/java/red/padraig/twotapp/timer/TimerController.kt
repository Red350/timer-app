package red.padraig.twotapp.timer

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.widget.NumberPicker
import android.widget.TextView
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import red.padraig.twotapp.alarm.AlarmSetter

/**
 * Created by Red on 26/09/2017.
 */
class TimerController(private val context: Context,
                      private val hourPicker: NumberPicker,
                      private val minutePicker: NumberPicker,
                      private val countDown: TextView,
                      sharedPrefs: SharedPreferences) {

    // TODO: Refactor this class to not take views as parameters

    val TICK_INTERVAL = 10L

    val timerModel: TimerModel = TimerModel(sharedPrefs)

    var timerTick: PublishSubject<Long> = PublishSubject.create()
    var timer: CountDownTimer? = null
    var currentMillisRemaining: Long = 0

    init {
        timerModel.hourChanged.subscribe { hourPicker.value = it }
        timerModel.minuteChanged.subscribe { minutePicker.value = it}

        hourPicker.setOnValueChangedListener { _,_,new -> timerModel.hour = new }
        minutePicker.setOnValueChangedListener { _,_,new -> timerModel.minute = new }
    }

    fun startTimer() {
        timer = createTimer(timerModel.millis).start()
    }

    fun pauseTimer() {
        timer?.cancel()
    }

    fun resumeTimer() {
        timer = createTimer(currentMillisRemaining).start()
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
                timerModel.hourChanged,
                timerModel.minuteChanged,
                BiFunction<Int, Int, Boolean> { hour, minute ->
                    !((hour == 0) && (minute == 0))
                }
        )
    }

    private fun createTimer(millis: Long): CountDownTimer {
        // Register broadcast with OS
        AlarmSetter.Impl().set(context, System.currentTimeMillis() + millis)

        return object : CountDownTimer(millis, TICK_INTERVAL) {
            override fun onFinish() {
                timerTick.onNext(-1)
            }

            override fun onTick(millisRemaining: Long) {
                timerTick.onNext(millisRemaining)
                currentMillisRemaining = millisRemaining
            }
        }
    }
}
