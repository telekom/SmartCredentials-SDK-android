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
package de.telekom.identityprovider.rest

import io.reactivex.Observable
import retrofit2.Retrofit

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
class RestController(baseUrl: String) {

    private val retrofitClient: Retrofit

    init {
        retrofitClient = RetrofitClient().createRetrofitClient(baseUrl)
    }

    fun getAccessToken(credentials: String): Observable<String> {
        val api = retrofitClient.create(
            PartnerManagementApi::class.java
        )
        return api.observeAccessToken(credentials)
    }

    fun getBearerToken(
        accessToken: String,
        clientId: String,
        packageName: String
    ): Observable<String> {
        val api = retrofitClient.create(
            PartnerManagementApi::class.java
        )
        val body = GetBearerBody(accessToken, clientId, null, packageName)
        return api.observeBearerToken(body)
    }
}