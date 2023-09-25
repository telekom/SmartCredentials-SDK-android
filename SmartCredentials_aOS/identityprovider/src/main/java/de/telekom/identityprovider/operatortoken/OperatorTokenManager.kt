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
import android.content.Intent
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
internal class OperatorTokenManager(private val providerPackageName: String) {

    companion object {
        const val INTENT_ACTION = "de.telekom.smartcredentials.oneclickbusiness.OPERATOR_TOKEN"
    }

    @Throws(Exception::class)
    fun getOperatorToken(
        context: Context,
        bearerToken: String,
        clientId: String,
        scope: String
    ): Single<SmartCredentialsResponse<String>> {
        return Single.defer {
            Single.create(
                OperatorTokenObservable(
                    context,
                    Intent(INTENT_ACTION).apply {
                        setPackage(providerPackageName)
                    }, bearerToken, clientId, scope
                )
            )
        }.subscribeOn(Schedulers.io())
    }
}