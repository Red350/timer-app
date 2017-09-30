package red.padraig.twotapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import red.padraig.twotapp.ui.activities.TimerCompleteActivity

/**
 * Created by Red on 29/09/2017.
 */
class AlarmBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "TimerWakelock")
        val newIntent = Intent(context, TimerCompleteActivity::class.java)
        wakeLock.acquire(1000)
        context.startActivity(newIntent)
        wakeLock.release()
    }
}