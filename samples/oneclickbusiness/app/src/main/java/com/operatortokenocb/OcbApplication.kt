package com.operatortokenocb

import android.app.Application
import timber.log.Timber


class OcbApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val configuration: SmartCredentialsConfiguration = SmartCredentialsConfiguration
            .Builder(applicationContext, "1234")
            .setRootCheckerEnabled(RootDetectionOption.ALL)
            .setAppAlias(applicationContext.packageName)
            .build()
        SmartCredentialsCoreFactory.initialize(configuration)
        val coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi()
        SmartCredentialsIdentityProviderFactory.initSmartCredentialsIdentityProviderModule(
            coreApi
        )
    }
}