/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.operatortokenocb

import android.app.Application
import de.telekom.identityprovider.factory.SmartCredentialsIdentityProviderFactory
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption
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
            coreApi,
            "de.telekom.smartcredentials.entitlements"
        )
    }
}