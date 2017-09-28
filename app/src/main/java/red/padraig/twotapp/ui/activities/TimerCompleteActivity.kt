package red.padraig.twotapp.ui.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_timer_complete.*
import red.padraig.twotapp.R
import red.padraig.twotapp.alarm.TimerAlarmAnnunciator

class TimerCompleteActivity : BaseActivity() {

    lateinit var timerAlarmAnnunciator: TimerAlarmAnnunciator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_complete)

        timerAlarmAnnunciator = TimerAlarmAnnunciator.Impl(applicationContext)
        timerAlarmAnnunciator.play()
    }

    override fun onStop() {
        super.onStop()
        stopAlarm()
    }

    override fun initialiseListeners() {
        button_timercomplete_stopalarm.setOnClickListener {
            this.stopAlarm()
            finish()
        }
    }

    override fun clearListeners() {
        button_timercomplete_stopalarm.setOnClickListener(null)
    }

    override fun initialiseSubscriptions() {
    }

    private fun stopAlarm() {
        timerAlarmAnnunciator.stop()
    }

}
