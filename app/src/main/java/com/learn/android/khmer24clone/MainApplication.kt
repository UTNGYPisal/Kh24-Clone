package com.learn.android.khmer24clone

import android.app.Application
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.learn.android.khmer24clone.di.retrofitModule
import com.learn.android.khmer24clone.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        startKoin {
            this.androidContext(this@MainApplication)
            loadKoinModules(listOf(retrofitModule, viewModelModules))
        }
    }
}

@GlideModule
class MyAppGlideModule : AppGlideModule()
