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

package de.telekom.identityprovider.apptoken

import de.telekom.identityprovider.rest.RestController
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class AppTokenManager(baseUrl: String) {

    private val restController: RestController

    init {
        restController = RestController(baseUrl)
    }

    fun getAccessToken(credentials: String): Single<String> {
        return restController.getAccessToken(credentials)
            .subscribeOn(Schedulers.io())
    }

    fun getBearerToken(accessToken: String, clientId: String, packageName: String): Single<String> {
        return restController.getBearerToken(accessToken, clientId, packageName)
            .subscribeOn(Schedulers.io())
    }
}