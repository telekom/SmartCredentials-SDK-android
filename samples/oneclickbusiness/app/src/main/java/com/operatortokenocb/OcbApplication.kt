package com.operatortokenocb

import android.app.Application
import de.telekom.identityprovider.factory.SmartCredentialsIdentityProviderFactory
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption
import timber.log.Timber


class OcbApplication: Application() {

    companion object {
        const val PARTNER_APPLICATION_URL = "https://lbl-partmgmr.superdtaglb.cf/"
        const val CREDENTIALS = "Moonlight-017e56d0-7997-44da-bac6-a3c3f4a42bea"
    }

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
            coreApi,
            PARTNER_APPLICATION_URL,
            CREDENTIALS
        )
    }
}