package com.learn.android.khmer24clone.common.helper

import android.app.Activity
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatEditText

fun printLog(message: String) {
    Log.d("KH24", message)
}

fun showKeyboard(editText: AppCompatEditText) {
    (editText.context as Activity).run {
        editText.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}