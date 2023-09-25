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

package de.telekom.identityprovider.factory

import de.telekom.identityprovider.controller.IdentityProviderController
import de.telekom.identityprovider.di.ObjectGraphCreatorIdentityProvider
import de.telekom.smartcredentials.core.api.CoreApi
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
@Suppress("unused")
object SmartCredentialsIdentityProviderFactory {

    private const val MODULE_NOT_INITIALIZED_EXCEPTION =
        "SmartCredentials IdentityProvider Module is not initialized"
    private var sIdentityProviderController: IdentityProviderController? = null

    @get:Synchronized
    val identityProviderApi: IdentityProviderApi
        get() {
            if (sIdentityProviderController == null) {
                throw RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION)
            }
            return sIdentityProviderController!!
        }

    @Synchronized
    @JvmStatic
    fun initSmartCredentialsIdentityProviderModule(
        coreApi: CoreApi,
        providerPackageName: String
    ): IdentityProviderApi {
        val coreController: CoreController = if (coreApi is CoreController) {
            coreApi
        } else {
            throw InvalidCoreApiException(SmartCredentialsModuleSet.IDENTITY_PROVIDER.moduleName)
        }
        sIdentityProviderController = ObjectGraphCreatorIdentityProvider.getInstance()
            .provideApiControllerIdentityProvider(coreController, providerPackageName)
        return sIdentityProviderController!!
    }

    fun clear() {
        ObjectGraphCreatorIdentityProvider.destroy()
        sIdentityProviderController = null
    }
}