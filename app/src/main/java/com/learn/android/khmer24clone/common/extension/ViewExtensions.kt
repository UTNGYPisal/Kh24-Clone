package com.learn.android.khmer24clone.common.extension

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.Fragment

fun Fragment.runOnUiThread(doSth: () -> Unit){
    Handler(Looper.getMainLooper()).post {
        doSth()
    }
}


fun Activity.showKeyboard(editText: EditText) {
    editText.requestFocus()
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}