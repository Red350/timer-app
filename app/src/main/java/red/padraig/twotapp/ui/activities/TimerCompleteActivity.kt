package red.padraig.twotapp.ui.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer_complete.*
import red.padraig.twotapp.R
import red.padraig.twotapp.alarm.TimerAlarm

class TimerCompleteActivity : BaseActivity() {

    lateinit var timerAlarm: TimerAlarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_complete)

        timerAlarm = TimerAlarm.Impl(applicationContext)
        timerAlarm.play()
    }

    override fun initialiseListeners() {
        button_timercomplete_stopalarm.setOnClickListener { this.stopAlarm() }
    }

    override fun clearListeners() {
        button_timercomplete_stopalarm.setOnClickListener(null)
    }

    override fun initialiseSubscriptions() {
    }

    private fun stopAlarm() {
        timerAlarm.stop()
    }

}
