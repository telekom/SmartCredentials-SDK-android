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

package de.telekom.smartcredentials.oneclickbusinessclient.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController
import de.telekom.smartcredentials.oneclickbusinessclient.operatortoken.OperatorTokenManager
import de.telekom.smartcredentials.oneclickbusinessclient.repository.StorageRepoImplementation
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModel
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModelFactory
import java.lang.ref.WeakReference

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
object ObjectGraphCreatorOneClickBusinessClient {

    private var sInstance: ObjectGraphCreatorOneClickBusinessClient? = null
    private var mOperatorTokenManager: OperatorTokenManager? = null

    fun getInstance(): ObjectGraphCreatorOneClickBusinessClient {
        if (sInstance == null) {
            sInstance = ObjectGraphCreatorOneClickBusinessClient
        }
        return sInstance!!
    }

    fun provideApiControllerOneClickBusinessClient(
        context: Context,
        coreController: CoreController,
        identityProviderApi: IdentityProviderApi,
        storageApi: StorageApi,
        config: OneClickClientConfiguration
    ): OneClickBusinessClientController {
        val storageRepo = StorageRepoImplementation(storageApi)
        mOperatorTokenManager =
            OperatorTokenManager(WeakReference(context), identityProviderApi, config)
        return OneClickBusinessClientController(
            coreController,
            storageRepo,
            config
        )
    }

    fun provideOperatorTokenManager(): OperatorTokenManager {
        if (mOperatorTokenManager == null) {
            throw Exception()
        }
        return mOperatorTokenManager!!
    }

    @Composable
    fun provideViewModelOneClickClient(): OneClickViewModel {
        return viewModel(factory = OneClickViewModelFactory(provideOperatorTokenManager())) as OneClickViewModel
    }

    fun destroy() {
        sInstance = null
    }
}
