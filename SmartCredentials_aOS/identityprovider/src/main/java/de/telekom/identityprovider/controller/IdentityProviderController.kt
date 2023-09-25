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

import android.annotation.SuppressLint
import android.content.Context
import de.telekom.identityprovider.apptoken.AppTokenManager
import de.telekom.identityprovider.operatortoken.OperatorTokenManager
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.core.identityprovider.IdentityProviderCallback
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable
import de.telekom.smartcredentials.core.responses.RootedThrowable
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
class IdentityProviderController(
    private val coreController: CoreController,
    private val providerPackageName: String
) : IdentityProviderApi {

    override fun getOperatorToken(
        context: Context,
        baseUrl: String,
        credentials: String,
        clientId: String,
        scope: String,
        callback: IdentityProviderCallback
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "getOperatorToken")
        val callbackReference = WeakReference(callback)
        if (coreController.isSecurityCompromised) {
            coreController.handleSecurityCompromised()
            callbackReference.get()?.onResult(SmartCredentialsResponse(RootedThrowable()))
            return
        }
        if (coreController.isDeviceRestricted(SmartCredentialsFeatureSet.IDENTITY_PROVIDER)) {
            callbackReference.get()?.onResult(
                SmartCredentialsResponse(
                    FeatureNotSupportedThrowable(
                        SmartCredentialsFeatureSet.IDENTITY_PROVIDER.notSupportedDesc
                    )
                )
            )
        }

        with(AppTokenManager(baseUrl)) {
            getAccessToken(credentials).flatMap {
                return@flatMap getBearerToken(
                    it,
                    clientId,
                    context.packageName
                )
            }.flatMap {
                return@flatMap getOperatorToken(
                    context,
                    it,
                    clientId,
                    scope,
                    true
                )
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callbackReference.get()?.onResult(it)
                }, {
                    callbackReference.get()?.onResult(SmartCredentialsResponse(it))
                })
        }
    }

    @SuppressLint("CheckResult")
    override fun getOperatorToken(
        context: Context,
        appToken: String,
        clientId: String,
        scope: String,
        callback: IdentityProviderCallback
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "getOperatorToken")
        val callbackReference = WeakReference(callback)
        getOperatorToken(context, appToken, clientId, scope, false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callbackReference.get()?.onResult(it)
            }, {
                callbackReference.get()?.onResult(SmartCredentialsResponse(it))
            })
    }

    private fun getOperatorToken(
        context: Context,
        appToken: String,
        clientId: String,
        scope: String,
        securityCheckPerformed: Boolean
    ): Single<SmartCredentialsResponse<String>> {

        if (!securityCheckPerformed) {
            if (coreController.isSecurityCompromised) {
                coreController.handleSecurityCompromised()
                return Single.just(SmartCredentialsResponse(RootedThrowable()))
            }
            if (coreController.isDeviceRestricted(SmartCredentialsFeatureSet.IDENTITY_PROVIDER)) {
                return Single.just(
                    SmartCredentialsResponse(
                        FeatureNotSupportedThrowable(
                            SmartCredentialsFeatureSet.IDENTITY_PROVIDER.notSupportedDesc
                        )
                    )
                )
            }
        }

        return OperatorTokenManager(providerPackageName).getOperatorToken(
            context,
            appToken,
            clientId,
            scope
            )
    }
}