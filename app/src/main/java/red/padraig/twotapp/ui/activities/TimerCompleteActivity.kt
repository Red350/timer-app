package red.padraig.twotapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_timer_complete.*
import red.padraig.twotapp.R
import red.padraig.twotapp.alarm.AlarmAnnunciator

class TimerCompleteActivity : BaseActivity() {

    lateinit var alarmAnnunciator: AlarmAnnunciator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_complete)

        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

        alarmAnnunciator = AlarmAnnunciator.Impl(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        alarmAnnunciator.play()
    }

    override fun onStop() {
        super.onStop()
        stopAlarm()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun initialiseListeners() {
        button_timercomplete_stopalarm.setOnClickListener {
            stopAlarm()
            finish()
        }
    }

    override fun clearListeners() {
        button_timercomplete_stopalarm.setOnClickListener(null)
    }

    override fun initialiseSubscriptions() {
    }

    private fun stopAlarm() {
        alarmAnnunciator.stop()
    }

}
