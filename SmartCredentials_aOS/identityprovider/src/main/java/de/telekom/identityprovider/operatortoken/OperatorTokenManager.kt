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
package de.telekom.identityprovider.operatortoken

import android.content.Context
import de.telekom.identityprovider.provider.ContentProviderManager

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
internal class OperatorTokenManager {

    @Throws(Exception::class)
    fun getOperatorToken(
        context: Context,
        bearerToken: String,
        clientId: String,
        scope: String
    ): String {
        val contentProviderManager = ContentProviderManager(context)
        return contentProviderManager.getOperatorToken(bearerToken, clientId, scope)
    }
}