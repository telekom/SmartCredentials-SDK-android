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
package de.telekom.smartcredentials.oneclickbusinessclient.factory

import android.content.Context
import de.telekom.smartcredentials.core.api.CoreApi
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.api.OneClickApi
import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController
import de.telekom.smartcredentials.oneclickbusinessclient.di.ObjectGraphCreatorOneClickBusinessClient

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Suppress("unused")
object SmartCredentialsOneClickBusinessClientFactory {

    private val MODULE_NOT_INITIALIZED_EXCEPTION =
        "SmartCredentials OneClickBusinessClient Module have not been initialized"
    private var sOneClickBusinessClientController: OneClickBusinessClientController? = null

    @JvmStatic
    @Synchronized
    fun initSmartCredentialsOneClickBusinessClientModule(
        context: Context,
        coreApi: CoreApi,
        identityProviderApi: IdentityProviderApi,
        storageApi: StorageApi,
        oneClickClientConfig: OneClickClientConfiguration
    ): OneClickApi {

        val coreController: CoreController = if (coreApi is CoreController) {
            coreApi
        } else {
            throw InvalidCoreApiException(SmartCredentialsModuleSet.ONE_CLICK_BUSINESS_CLIENT_MODULE.moduleName)
        }

        sOneClickBusinessClientController =
            ObjectGraphCreatorOneClickBusinessClient.getInstance()
                .provideApiControllerOneClickBusinessClient(
                    context,
                    coreController,
                    identityProviderApi,
                    storageApi,
                    oneClickClientConfig
                )
        return sOneClickBusinessClientController as OneClickBusinessClientController
    }

    @JvmStatic
    @get:Synchronized
    val oneClickBusinessClientApi: OneClickApi
        get() {
            if (sOneClickBusinessClientController == null) {
                throw RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION)
            }
            return sOneClickBusinessClientController!!
        }

    fun clear() {
        ObjectGraphCreatorOneClickBusinessClient.destroy()
        sOneClickBusinessClientController = null
    }
}