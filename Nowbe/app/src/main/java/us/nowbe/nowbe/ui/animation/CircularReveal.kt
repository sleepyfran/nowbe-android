package us.nowbe.nowbe.ui.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import io.codetail.animation.ViewAnimationUtils
import us.nowbe.nowbe.utils.AnimationUtils

/**
 * This file is part of Nowbe for Android
 *
 * Copyright (c) 2016 The Nowbe Team
 * Maintained by Fran González <@spaceisstrange>
 */

class CircularReveal {
    companion object {
        /**
         * Makes the enter animation with a circular reveal of the root view
         */
        fun showEnterRevealAnimation(view: ViewGroup, onEnd: () -> Unit,
                                     cx: Int = (view.left + view.right) / 2,
                                     cy: Int = (view.top + view.right) / 2) {

            // Get the final radius
            val finalRadius = (view.width + view.width).toFloat()

            // Create the circular reveal animation
            val rootRevealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0.toFloat(), finalRadius)
            rootRevealAnimator.interpolator = AccelerateDecelerateInterpolator()
            rootRevealAnimator.duration = AnimationUtils.DEFAULT_LENGTH

            // Add a listener to notify about the end of the animation
            rootRevealAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    // Finally call the specified onEnd
                    onEnd()
                }
            })

            // Show the view an the animation
            view.visibility = View.VISIBLE
            rootRevealAnimator.start()
        }

        /**
         * Makes the exit animation with a reverse circular reveal of the root view and execute the function passed
         * as a parameter
         */
        fun showExitRevealAnimation(view: ViewGroup, onEnd: () -> Unit,
                                    cx: Int = (view.left + view.right) / 2,
                                    cy: Int = (view.top + view.right) / 2) {

            // Get the initial radius
            val initialRadius = (view.width + view.width).toFloat()

            // Create the circular reveal animation
            val rootRevealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0.toFloat())
            rootRevealAnimator.interpolator = AccelerateDecelerateInterpolator()
            rootRevealAnimator.duration = AnimationUtils.DEFAULT_LENGTH

            // Add a listener to notify about the end of the animation
            rootRevealAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animator: Animator?) {
                    // Finally hide the view and call the specified onEnd
                    view.visibility = View.INVISIBLE
                    onEnd()
                }
            })

            rootRevealAnimator.start()
        }
    }
}