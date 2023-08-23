package com.viplearner.notes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class VIPNotesApp : Application(){
    override fun onCreate() {

        Timber.plant(Timber.DebugTree())

        super.onCreate()
    }

}