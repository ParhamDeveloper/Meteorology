package com.parhambaghebani.utility

import android.content.res.Resources

fun Int.toDP(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPX(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.toDP(): Float = (this / Resources.getSystem().displayMetrics.density)

fun Float.toPX(): Float = (this * Resources.getSystem().displayMetrics.density)