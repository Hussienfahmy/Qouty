package com.hussien.qouty.ext

import android.animation.Animator
import android.view.ViewPropertyAnimator

//todo move this package as library
fun ViewPropertyAnimator.addListener(
    onStart: (animation: Animator?) -> Unit = {},
    onCancel: (animation: Animator?) -> Unit = {},
    onRepeat: (animation: Animator?) -> Unit = {},
    onEnd: (animation: Animator?) -> Unit
    ) {
    setListener(object: Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            onStart(animation)
        }

        override fun onAnimationEnd(animation: Animator?) {
            onEnd(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {
            onCancel(animation)
        }

        override fun onAnimationRepeat(animation: Animator?) {
            onRepeat(animation)
        }
    })
}