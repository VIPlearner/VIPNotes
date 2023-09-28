package com.viplearner.notes

import android.app.Application
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class VIPNotesApp : Application(){
    override fun onCreate() {

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Firebase.database.setPersistenceEnabled(true)

        super.onCreate()
    }

}