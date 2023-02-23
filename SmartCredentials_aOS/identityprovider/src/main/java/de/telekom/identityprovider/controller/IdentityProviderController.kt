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
package de.telekom.identityprovider.controller

import android.content.Context
import de.telekom.identityprovider.operatortoken.OperatorTokenManager
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable
import de.telekom.smartcredentials.core.responses.RootedThrowable
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
class IdentityProviderController(
    private val mCoreController: CoreController,
    private val baseUrl: String,
    private val credentials: String
) : IdentityProviderApi {

    override fun getOperatorToken(
        context: Context,
        clientId: String,
        scope: String
    ): SmartCredentialsApiResponse<String> {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "getOperatorToken")
        if (mCoreController.isSecurityCompromised) {
            mCoreController.handleSecurityCompromised()
            return SmartCredentialsResponse(RootedThrowable())
        }
        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.IDENTITY_PROVIDER)) {
            return SmartCredentialsResponse(
                FeatureNotSupportedThrowable(
                    SmartCredentialsFeatureSet.IDENTITY_PROVIDER.notSupportedDesc
                )
            )
        }

        with(OperatorTokenManager(baseUrl)) {
            return try {
                SmartCredentialsResponse(
                    getOperatorToken(
                        context,
                        getBearerToken(
                            getAccessToken(credentials),
                            clientId,
                            context.packageName
                        ),
                        clientId,
                        scope
                    )
                )
            } catch (e: Exception) {
                SmartCredentialsResponse(e)
            }
        }
    }
}