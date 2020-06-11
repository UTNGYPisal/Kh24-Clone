package com.learn.android.khmer24clone.common.extension

import android.text.format.DateUtils
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

val String.readableRelativeDate: String?
get() {
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this)
    return DateUtils.getRelativeTimeSpanString(date.time, Date().time, DateUtils.DAY_IN_MILLIS).toString()
}


fun Double.toUSDCurrency(): String {
    val currency = NumberFormat.getCurrencyInstance().format(this)
    return "$${currency.replace("KHR", "")?.replace(Regex("\\p{Sc}"), "")}"
}