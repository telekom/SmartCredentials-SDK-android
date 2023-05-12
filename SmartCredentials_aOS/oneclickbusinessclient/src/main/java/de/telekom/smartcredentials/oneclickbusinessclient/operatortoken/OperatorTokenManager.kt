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

package de.telekom.smartcredentials.oneclickbusinessclient.operatortoken

import android.content.Context
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.identityprovider.IdentityProviderCallback
import de.telekom.smartcredentials.oneclickbusinessclient.BuildConfig
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import java.lang.ref.WeakReference

class OperatorTokenManager(
    private val contextReference: WeakReference<Context>,
    private val identityProviderApi: IdentityProviderApi,
    private val config: OneClickClientConfiguration
) {

    fun requestOperatorToken(callback: IdentityProviderCallback) {
        contextReference.get()?.let {
            identityProviderApi.getOperatorToken(
                it,
                config.serverUrl,
                config.credentials,
                BuildConfig.CLIENT_ID,
                BuildConfig.SCOPE,
                callback
            )
        } ?: throw Exception()
    }
}