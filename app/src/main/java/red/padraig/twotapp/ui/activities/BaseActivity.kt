package red.padraig.twotapp.ui.activities

import android.app.Activity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Red on 06/09/2017.
 */
abstract class BaseActivity : Activity() {

    val disposables = CompositeDisposable()

    override fun onResume() {
        super.onResume()
        initialiseListeners()
        initialiseSubscriptions()
    }

    override fun onPause() {
        clearSubscriptions()
        clearListeners()
        super.onPause()
    }

    protected abstract fun initialiseListeners()

    protected abstract fun clearListeners()

    protected abstract fun initialiseSubscriptions()

    protected fun clearSubscriptions() {
        disposables.clear()
    }
}