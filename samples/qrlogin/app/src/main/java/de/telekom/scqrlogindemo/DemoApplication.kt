package de.telekom.scqrlogindemo

import android.app.Application
import com.facebook.stetho.Stetho
import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory
import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption
import de.telekom.smartcredentials.networking.factory.SmartCredentialsNetworkingFactory
import de.telekom.smartcredentials.qrlogin.factory.SmartCredentialsQrLoginFactory
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory
import timber.log.Timber
import timber.log.Timber.DebugTree

class DemoApplication : Application() {
    companion object {
        const val TAG = "qrlogin_tag"
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        Stetho.initializeWithDefaults(this)
        initSmartCredentialsModules()
    }

    private fun initSmartCredentialsModules() {
        val configuration = SmartCredentialsConfiguration.Builder(
            applicationContext,
            getString(R.string.current_user_id)
        )
            .setLogger(DemoLogger())
            .setRootCheckerEnabled(RootDetectionOption.ALL)
            .setAppAlias(getString(R.string.app_alias))
            .build()
        SmartCredentialsCoreFactory.initialize(configuration)
        val coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi()
        val securityApi =
            SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi)
        val storageApi = SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(
            this, coreApi,
            securityApi
        )
        val authorizationApi =
            SmartCredentialsAuthorizationFactory.initSmartCredentialsAuthorizationModule(
                coreApi, securityApi, storageApi
            )
        val networkingApi =
            SmartCredentialsNetworkingFactory.initSmartCredentialsNetworkingModule(coreApi)
        SmartCredentialsQrLoginFactory.initSmartCredentialsQrLoginModule(
            coreApi,
            authorizationApi, networkingApi
        )
        SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi)
    }
}