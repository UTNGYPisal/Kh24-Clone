package com.learn.android.khmer24clone

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.learn.android.khmer24clone.di.retrofitModule
import com.learn.android.khmer24clone.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        instance = this
        FacebookSdk.sdkInitialize(applicationContext)
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        AppEventsLogger.activateApp(this)

        startKoin {
            this.androidContext(this@MainApplication)
            loadKoinModules(listOf(retrofitModule, viewModelModules))
        }
    }

    companion object {
        lateinit var instance: MainApplication
        private set
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()
