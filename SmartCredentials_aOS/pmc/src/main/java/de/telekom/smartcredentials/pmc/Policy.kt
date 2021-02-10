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

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Alex.Graur@endava.com at 2/9/2021
 */
@JsonClass(generateAdapter = true)
data class PolicySchema(
        @Json(name = "PolicySchemaId") val policySchemaId: Int = 0,
        @Json(name = "PolicySchemaContent") val policySchemaContent: String? = null,
        @Json(name = "TimeStamp") val timestamp: String = "",
        @Json(name = "isEnabled") var isEnabled: Boolean = false,
        @Json(name = "SubPolicies") var subPolicies: List<String>? = listOf("")
)

@JsonClass(generateAdapter = true)
data class PolicySchemaResponse(
        @Json(name = "@odata.context") val odataContext: String,
        @Json(name = "value") val value: List<PolicySchema>
)

@JsonClass(generateAdapter = true)
data class NewPolicy(
        @Json(name = "PolicySchemaId") val policySchemaId: Int = 0,
        @Json(name = "PolicySchemaContent") val policySchemaContent: Policy? = null
)

@JsonClass(generateAdapter = true)
data class Policy(
        @Json(name = "@context") val context: Map<String, String>? = null,
        @Json(name = "@id") val id: String = "0",
        @Json(name = "title") val title: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "dpv:PersonalDataCategory") val dpvPersonalDataCategory: List<String>? = null,
        @Json(name = "dpv:Purpose") val dpvPurpose: String? = null,
        @Json(name = "dpv:Processing") val dpvProcessing: List<String>? = null,
        @Json(name = "dpv:TechnicalOrganisationalMeasure") val dpvTechnicalOrganisationalMeasure:
        DpvTechnicalOrganisationalMeasure? = null,
        @Json(name = "dpv:Recipient") val dpvRecipient: DpvRecipient? = null
)

data class SubPolicy(
        val name: String = "",
        var isEnabled: Boolean = false
)

@JsonClass(generateAdapter = true)
data class DpvRecipient(
        @Json(name = "@type") val type: String? = null,
        @Json(name = "legalName") val legalName: String? = null,
        @Json(name = "name") val name: String? = null,
        @Json(name = "description") val description: String? = null,
        @Json(name = "address") val address: String? = null,
        @Json(name = "vatID") val vatID: String? = null,
        @Json(name = "identifier") val identifier: String? = null,
        @Json(name = "url") val url: String? = null
)

@JsonClass(generateAdapter = true)
data class DpvTechnicalOrganisationalMeasure(
        @Json(name = "dpv:TechnicalMeasure") val dpvTechnicalMeasure: DpvTechnicalMeasure? = null
)

@JsonClass(generateAdapter = true)
data class DpvTechnicalMeasure(
        @Json(name = "dpv:StorageRestriction") val dpvStorageRestriction: DpvStorageRestriction? = null
)

@JsonClass(generateAdapter = true)
data class DpvStorageRestriction(
        @Json(name = "dpv:StorageDuration") val dpvStorageDuration: String? = null,
        @Json(name = "dpv:StorageLocation") val dpvStorageLocation: String? = null
)

@JsonClass(generateAdapter = true)
data class JsonPolicies(
        @Json(name = "policies") val jsonPolicies: List<PolicySchema>
)

@JsonClass(generateAdapter = true)
data class UserPolicyResponse(
        @Json(name = "@odata.context") val odataContext: String,
        @Json(name = "value") val value: List<UserPolicy>
)

@JsonClass(generateAdapter = true)
data class UserPolicy(
        @Json(name = "UserId") val userId: String = "",
        @Json(name = "FirebaseID") val firebaseId: String = "",
        @Json(name = "UserPolicy1") val userPolicy: String = "",
        @Json(name = "PolicyIds") val policyIds: String = "",
        @Json(name = "TimeStamp") val timestamp: String = ""
)

@JsonClass(generateAdapter = true)
data class UserPolicyHistoryResponse(
        @Json(name = "historyType") val historyType: String = "",
        @Json(name = "policyId") val policyId: Int = 0,
        @Json(name = "value") val value: String = "",
        @Json(name = "createdDate") val createdDate: String = "",
        @Json(name = "modifiedDate") val modifiedDate: String = ""
)

@JsonClass(generateAdapter = true)
data class UserDataUsageResponse(
        @Json(name = "@odata.context") val odataContext: String,
        @Json(name = "value") val value: List<UserDataUsage>
)

@JsonClass(generateAdapter = true)
data class UserDataUsage(
        @Json(name = "Client") val client: String = "",
        @Json(name = "DataType") val dataType: String = "",
        @Json(name = "Success") val success: Boolean = false,
        @Json(name = "Reason") val reason: String? = "",
        @Json(name = "TimeStamp") val timeStamp: String = ""
)

@JsonClass(generateAdapter = true)
data class UserInformation(
        @Json(name = "userId") val userId: String = "",
        @Json(name = "firebaseID") val firebaseId: String = "",
        @Json(name = "sessionId") val sessionId: String = "",
        @Json(name = "timeStamp") val timestamp: String = "",
        @Json(name = "phoneNo") val phoneNo: String = "",
        @Json(name = "isWifiOn") val isWifiOn: Boolean = true,
        @Json(name = "isDataOn") val isDataOn: Boolean = true,
        @Json(name = "manufacturerName") val manufacturerName: String = "",
        @Json(name = "deviceName") val deviceName: String = "",
        @Json(name = "imeiNumber") val imeiNumber: String = "",
        @Json(name = "operatorName") val operatorName: String = "",
        @Json(name = "osVersion") val osVersion: String = "",
        @Json(name = "latitude") val latitude: Double = 0.0,
        @Json(name = "longitude") val longitude: Double = 0.0,
        @Json(name = "altitude") val altitude: Double = 0.0,
        @Json(name = "accuracy") val accuracy: Double = 0.0,
        @Json(name = "speed") val speed: Double = 0.0
)