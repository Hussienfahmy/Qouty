package com.hussien.quoty.ext

import android.animation.Animator
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

/**
 * To bitmap.
 * 
 * convert the view to bitmap with the same width and height of the view
 * @receiver [View] to be converted to bitmap
 * @return [Bitmap] of the view
 */
fun View.toBitmap(): Bitmap {
    val displayMetrics = context.resources.displayMetrics

    measure(displayMetrics.widthPixels, displayMetrics.heightPixels)

    val bitmap: Bitmap = Bitmap.createBitmap(
        measuredWidth, measuredHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    layout(0, 0, measuredWidth, measuredHeight)
    draw(canvas)
    return bitmap
}

/**
 * Duplicate.
 *
 * @receiver [T] view which want ot duplicate
 * @return [T] duplicate the view with same attributes and layoutParams
 */
fun <T: View> T.duplicate(): T {
    val duplicate = clone()
    duplicate.layoutParams = this.layoutParams
    return duplicate
}

/**
 * Clone.
 * 
 * supports [TextView], [ImageView], [EditText]
 * @receiver [T] the view which want to clone it
 * @return [T] a clone (copy) of the view [T] without any params
 */
private fun <T: View> T.clone(): T {
    val cloned = when(this) {
        is TextView -> TextView(context).apply {
            text = this@clone.text
            textSize = this@clone.textSize
            typeface = this@clone.typeface
        }
        is ImageView -> ImageView(context).apply {
            setImageDrawable(this@clone.drawable)
        }
        is EditText -> EditText(context).apply {
            text = this@clone.text
            text = this@clone.text
            textSize = this@clone.textSize
            typeface = this@clone.typeface
        }
        else -> throw IllegalStateException("Unsupported View")
    }

    return cloned as T
}

/**
 * Add to parent of.
 * 
 * add the receiver to the parent of the supplied view
 * @receiver [View] view to be added
 * @param view View to add to it's parent
 * @return [View] the added view
 */
fun View.addToParentOf(view: View): View {
    val parent = view.parent as ViewGroup
    parent.addView(this)
    return this
}

/**
 * Scale with fading.
 *
 * apply animation to the receiver to scale and fade
 * @receiver [View]
 * @param onEnd On end block execute after the animation ends
 */
fun View.scaleWithFading(scaleTo: Float, fadeTo: Float, onEnd: (animation: Animator?) -> Unit = {}) {
    animate()
        .scaleXBy(scaleTo)
        .scaleYBy(scaleTo)
        .alpha(fadeTo)
        .setDuration(500)
        .addListener {
            onEnd(it)
        }
}