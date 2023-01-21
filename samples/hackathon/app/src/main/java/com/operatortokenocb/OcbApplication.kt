package com.operatortokenocb

import android.app.Application
import timber.log.Timber


class OcbApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}