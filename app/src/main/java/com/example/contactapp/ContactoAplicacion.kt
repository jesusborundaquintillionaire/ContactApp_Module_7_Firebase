package com.example.contactapp

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import timber.log.Timber

class ContactoAplicacion : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}