package com.example.ch3mxr.base

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.setClientToken("0709dd60d6bebd73ffb07c2ab660aa76")
        FacebookSdk.sdkInitialize(this)
    }
}