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
package de.telekom.identityprovider.di

import de.telekom.identityprovider.controller.IdentityProviderController
import de.telekom.smartcredentials.core.controllers.CoreController

object ObjectGraphCreatorIdentityProvider {

    private var sInstance: ObjectGraphCreatorIdentityProvider? = null

    fun getInstance(): ObjectGraphCreatorIdentityProvider {
        if (sInstance == null) {
            sInstance = ObjectGraphCreatorIdentityProvider
        }
        return sInstance as ObjectGraphCreatorIdentityProvider
    }


    fun provideApiControllerIdentityProvider(
        coreController: CoreController,
        baseUrl: String,
        credentials: String
    ): IdentityProviderController {
        return IdentityProviderController(coreController, baseUrl, credentials)
    }

    fun destroy() {
        sInstance = null
    }
}