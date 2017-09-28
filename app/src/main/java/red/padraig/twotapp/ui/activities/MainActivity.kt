package red.padraig.twotapp.ui.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import red.padraig.twotapp.R
import red.padraig.twotapp.extensions.getHours
import red.padraig.twotapp.extensions.getMilliseconds
import red.padraig.twotapp.extensions.getMinutes
import red.padraig.twotapp.extensions.getSeconds
import red.padraig.twotapp.timer.TimerController

class MainActivity : BaseActivity() {

    lateinit var timerController: TimerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerController = TimerController(numberpicker_main_hour, numberpicker_main_minute, textview_main_countdown, getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE) )
        numberpicker_main_hour.minValue = 0
        numberpicker_main_hour.maxValue = 11
        numberpicker_main_minute.minValue = 0
        numberpicker_main_minute.maxValue = 59
    }

    override fun onResume() {
        super.onResume()
        timerController.restore()
    }

    override fun onPause() {
        super.onPause()
        timerController.save()
    }

    override fun initialiseListeners() {
        button_main_starttimer.setOnClickListener { this.startTimer() }
        button_main_pausetimer.setOnClickListener { this.pauseTimer() }
        button_main_resumetimer.setOnClickListener { this.resumeTimer() }
        button_main_resettimer.setOnClickListener { this.resetTimer() }
    }

    override fun clearListeners() {
        button_main_starttimer.setOnClickListener(null)
        button_main_pausetimer.setOnClickListener(null)
        button_main_resumetimer.setOnClickListener(null)
        button_main_resettimer.setOnClickListener(null)
    }

    override fun initialiseSubscriptions() {
        disposables.addAll(
                timerController.timerTick.subscribe(this::updateCountdown),
                timerController.createEnableStartButtonFlowable().subscribe(this::enableStartButton)
        )
    }

    fun startTimer() {
        setCountdownVisibility(true)
        button_main_starttimer.visibility = View.GONE
        button_main_pausetimer.visibility = View.VISIBLE
        timerController.startTimer()
    }

    fun pauseTimer() {
        button_main_pausetimer.visibility = View.GONE
        button_main_resumetimer.visibility = View.VISIBLE
        button_main_resettimer.visibility = View.VISIBLE
        timerController.pauseTimer()
    }

    fun resumeTimer() {
        button_main_resumetimer.visibility = View.GONE
        button_main_pausetimer.visibility = View.VISIBLE
        button_main_resettimer.visibility = View.INVISIBLE
        timerController.resumeTimer()
    }

    fun resetTimer() {
        setCountdownVisibility(false)
        button_main_resumetimer.visibility = View.GONE
        button_main_pausetimer.visibility = View.GONE
        button_main_resettimer.visibility = View.INVISIBLE
        button_main_starttimer.visibility = View.VISIBLE
        timerController.resetTimer()
    }

     fun updateCountdown(millisRemaining: Long) {
         when (millisRemaining) {
             -1L -> ringAlarm()
             else -> {
                 val builder = StringBuilder()
                 builder.append(millisRemaining.getHours())
                 builder.append("h ")
                 builder.append(millisRemaining.getMinutes())
                 builder.append("m ")
                 builder.append(millisRemaining.getSeconds())
                 builder.append("s ")
                 builder.append(String.format("%03d", millisRemaining.getMilliseconds()))
                 textview_main_countdown.text = builder.toString()
             }
         }
    }

    private fun ringAlarm() {
        resetTimer()
        textview_main_countdown.text = "Alarm!!!!"
    }

    private fun setCountdownVisibility(visible: Boolean) {
        if (visible) {
            textview_main_countdown.visibility = View.VISIBLE
            linearlayout_main_setcountdown.visibility = View.GONE
        } else {
            textview_main_countdown.visibility = View.GONE
            linearlayout_main_setcountdown.visibility = View.VISIBLE
        }
    }

    private fun enableStartButton(enable: Boolean) {
        button_main_starttimer.isEnabled = enable
    }

}