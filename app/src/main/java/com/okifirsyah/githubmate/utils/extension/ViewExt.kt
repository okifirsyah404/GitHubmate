package com.okifirsyah.githubmate.utils.extension

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.okifirsyah.githubmate.resource.constant.DurationConstant

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.popTap(duration: Long = DurationConstant.POP_TAP_ANIMATE_DURATION) {
    this.visibility = View.VISIBLE
    this.alpha = 1.0f

    this.pivotX = (this.width / 2).toFloat()
    this.pivotY = (this.height / 2).toFloat()

    val scaleDownX = ObjectAnimator.ofFloat(this, "scaleX", 0.7f)
    val scaleDownY = ObjectAnimator.ofFloat(this, "scaleY", 0.7f)

    scaleDownX.duration = duration / 2
    scaleDownY.duration = duration / 2

    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f)
    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f)

    scaleUpX.duration = duration / 2
    scaleUpY.duration = duration / 2

    val scaleDown = AnimatorSet()
    scaleDown.play(scaleDownX).with(scaleDownY)
    scaleDown.start()

    val scaleUp = AnimatorSet()
    scaleUp.play(scaleUpX).with(scaleUpY).after(scaleDown)
    scaleUp.start()
}

fun View.animateSplash(duration: Long = DurationConstant.SPLASH_ANIMATE_DURATION) {
    val animatorSet = AnimatorSet()

    // Set initial visibility and alpha for a smooth appearance
    this.visibility = View.VISIBLE
    this.alpha = 0.0f

    // Scale up animators
    val scaleUpX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f)
    scaleUpX.duration = duration / 2

    val scaleUpY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f)
    scaleUpY.duration = duration / 2

    // Fade in animator
    val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 1.0f)
    fadeIn.duration = duration / 2

    // Combine animations
    animatorSet.play(scaleUpX).with(scaleUpY).with(fadeIn)

    animatorSet.start()
}
