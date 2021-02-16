/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.pmc

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Alex.Graur@endava.com at 2/10/2021
 */
interface PolicyService {

    companion object {
        const val POLICIES_API_URL = "https://dtpolicyapi.azurewebsites.net/"
    }

    @GET("/odata/PolicySchema?\$filter")
    fun getPolicySchemas(@Query("\$filter") userId: String): Observable<PolicySchemaResponse>

    @POST("/UserPolicy")
    fun postPoliciesAsync(@Body userPolicy: UserPolicy): Observable<Void>

    @POST("/UserPolicy/Update")
    fun updatePoliciesAsync(@Body userPolicy: UserPolicy): Observable<Void>

    @GET("/odata/UserPolicy")
    fun getUserPolicy(@Query("\$filter") userId: String): Observable<UserPolicyResponse>

    @POST("/History/UserHistory")
    fun getUserPolicyHistory(@Query("userId") userId: String): Observable<List<UserPolicyHistoryResponse>>

    @GET("/odata/UserAuditData")
    fun getUserDataUsage(@Query("filter") userId: String): Observable<UserDataUsageResponse>

    @POST("/UserInformation")
    fun postUserInformationAsync(@Body userInformation: UserInformation): Observable<Void>

    @POST("/UserInformation/Update")
    fun updateUserInformationAsync(@Body userInformation: UserInformation): Observable<Void>
}