package red.padraig.twotapp.ui.animators

import android.animation.Animator
import android.view.View

/**
 * Created by Red on 01/10/2017.
 */
class ViewCrossFader(val fadeIn: View, val fadeOut: View) {

    fun fade(duration: Long) {
        fadeIn.visibility = View.VISIBLE
        fadeIn.alpha = 0f
        fadeIn.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null)

        fadeOut.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener( object: Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {}
                    override fun onAnimationEnd(p0: Animator?) {
                        // TODO: Change this to GONE, update views to remain static
                        fadeOut.visibility = View.INVISIBLE
                    }
                    override fun onAnimationCancel(p0: Animator?) {}
                    override fun onAnimationStart(p0: Animator?) {}
                })
    }
}