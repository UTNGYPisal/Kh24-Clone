/*
 * Copyright (c) 2020. Pisal@WB Finance
 * All rights reserved.
 */

package com.learn.android.khmer24clone.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import java.util.*


class MainNotificationService : FirebaseMessagingService() {

    private var mNotificationManager: NotificationManager? = null


    override fun onCreate() {
        super.onCreate()

        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val mChannel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH)
            mChannel.enableVibration(false)
            mChannel.enableLights(false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mChannel.setAllowBubbles(true)
            }

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager?.createNotificationChannel(mChannel)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        printLog("MBNotificationService>onNewToken: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        printLog("onMessageReceived: ${remoteMessage.notification}")
        printLog("onMessageReceived: ${remoteMessage.data}")
        val prodId = remoteMessage.data.getValue("product_id").toInt()
        val bundle = bundleOf("id" to prodId)

        showLocalNotification(remoteMessage.notification?.title, remoteMessage.notification?.body, bundle)
    }

    private fun showLocalNotification(title: String?, body: String?, bundle: Bundle? = null) {
        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.productDetailFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val notification: Notification?
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setOngoing(false)
            .setAutoCancel(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            builder.setTimeoutAfter(3000)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        notification =  builder.build()
        mNotificationManager!!.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "channel_01"
        val NOTIFICATION_ID: Int
        get() {
            return Date().time.toInt()
        }
        const val SP_NOTIFICATION_COUNT_KEY = "notificationCount"
    }
}