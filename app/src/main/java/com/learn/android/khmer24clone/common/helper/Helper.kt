package com.learn.android.khmer24clone.common.helper

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager

fun printLog(message: String) {
    Log.d("KH24", message)
}

fun hideSoftKeyboard(activity: Activity) {
    try {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    } catch (e: Exception) {
    }
}