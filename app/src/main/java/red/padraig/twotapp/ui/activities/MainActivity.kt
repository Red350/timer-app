package red.padraig.twotapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import red.padraig.twotapp.R
import red.padraig.twotapp.extensions.getHours
import red.padraig.twotapp.extensions.getMilliseconds
import red.padraig.twotapp.extensions.getMinutes
import red.padraig.twotapp.extensions.getSeconds
import red.padraig.twotapp.timer.TimerController
import red.padraig.twotapp.ui.animators.ViewCrossFader


class MainActivity : BaseActivity() {

    var shortAnimationDuration = 0
    lateinit var timerController: TimerController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCountdownVisibility(false)
        initialiseNumberPickers()
        timerController = TimerController(this)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        displayTimerIfRunningOrPaused()
    }

    override fun onResume() {
        super.onResume()
        timerController.restoreSettings()
    }

    override fun onPause() {
        super.onPause()
        timerController.saveSettings()
        timerController
    }

    override fun initialiseListeners() {
        button_main_starttimer.setOnClickListener { startTimer() }
        button_main_pausetimer.setOnClickListener { pauseTimer() }
        button_main_resumetimer.setOnClickListener { resumeTimer() }
        button_main_resettimer.setOnClickListener { resetTimer() }
    }

    override fun clearListeners() {
        button_main_starttimer.setOnClickListener(null)
        button_main_pausetimer.setOnClickListener(null)
        button_main_resumetimer.setOnClickListener(null)
        button_main_resettimer.setOnClickListener(null)
    }

    override fun initialiseSubscriptions() {
        disposables.addAll(
                timerController.timerTick.subscribe(this::onCountdownTick),
                timerController.createEnableStartButtonFlowable().subscribe(this::enableStartButton)
        )
    }

    private fun initialiseNumberPickers() {
        numberpicker_main_hours.minValue = 0
        numberpicker_main_hours.maxValue = 11
        numberpicker_main_minutes.minValue = 0
        numberpicker_main_minutes.maxValue = 50
        numberpicker_main_seconds.minValue = 0
        numberpicker_main_seconds.maxValue = 59
    }

    fun startTimer() {
        setCountdownVisibility(true)
        button_main_starttimer.visibility = View.INVISIBLE
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

     fun onCountdownTick(millisRemaining: Long) {
         updateCountdown(millisRemaining)
         if (millisRemaining == 0L)
             ringAlarm()
    }

    private fun updateCountdown(millisRemaining: Long) {
        // todo move this to TimerController
        val builder = StringBuilder()
        builder.append(millisRemaining.getHours())
        builder.append("h ")
        builder.append(String.format("%02d", millisRemaining.getMinutes()))
        builder.append("m ")
        builder.append(String.format("%02d", millisRemaining.getSeconds()))
        builder.append("s ")
        builder.append(String.format("%03d", millisRemaining.getMilliseconds()))
        textview_main_displaycountdown.text = builder.toString()
    }

    private fun ringAlarm() {
        timerController.resetTimer()
        val intent = Intent(this, TimerCompleteActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setCountdownVisibility(visible: Boolean) {
        if (visible) {
            ViewCrossFader(textview_main_displaycountdown, linearlayout_main_setcountdown)
                    .fade(shortAnimationDuration.toLong())
        } else {
            ViewCrossFader(linearlayout_main_setcountdown, textview_main_displaycountdown)
                    .fade(shortAnimationDuration.toLong())
        }
    }

    private fun enableStartButton(enable: Boolean) {
        button_main_starttimer.isEnabled = enable
    }

    private fun displayTimerIfRunningOrPaused() {
        if (timerController.timerActive) {
            startTimer()
        } else if (timerController.timerPaused) {
            startTimer()
            pauseTimer()
        }
    }

}
