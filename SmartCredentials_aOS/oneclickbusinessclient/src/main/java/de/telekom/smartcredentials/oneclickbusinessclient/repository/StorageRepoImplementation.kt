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
package de.telekom.smartcredentials.oneclickbusinessclient.repository

import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.context.ItemContext
import de.telekom.smartcredentials.core.context.ItemContextFactory
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.oneclickbusinessclient.recommendation.Recommendation
import org.json.JSONObject

class StorageRepoImplementation(
    private val mStorageApi: StorageApi
) : StorageRepo {

    private val itemContextToken: ItemContext =
        ItemContextFactory.createEncryptedSensitiveItemContext(
            KEY_1CB_CLIENT_CONFIG_TYPE_TOKEN
        )

    private val itemContextRecommendation: ItemContext =
        ItemContextFactory.createEncryptedSensitiveItemContext(
            KEY_1CB_CLIENT_CONFIG_TYPE_RECOMMENDATION
        )
    private val mTokenFilter: SmartCredentialsFilter =
        SmartCredentialsFilterFactory.createSensitiveItemFilter(
            KEY_1CB_CLIENT_CONFIG_ID_TOKEN, KEY_1CB_CLIENT_CONFIG_TYPE_TOKEN
        )
    private var storageTokenList: List<ItemEnvelope> = ArrayList()

    private val mRecommendationFilter: SmartCredentialsFilter =
        SmartCredentialsFilterFactory.createSensitiveItemFilter(
            KEY_1CB_CLIENT_CONFIG_ID_RECOMMENDATION, KEY_1CB_CLIENT_CONFIG_TYPE_RECOMMENDATION
        )
    private var recommendationList: List<ItemEnvelope> = ArrayList()

    init {
        updateTokenList()
        updateRecommendationList()
    }

    companion object {
        private const val KEY_1CB_CLIENT_CONFIG_TYPE_TOKEN = "1cb_client_config_token"
        private const val KEY_1CB_CLIENT_CONFIG_TYPE_RECOMMENDATION =
            "1cb_client_config_recommendation"
        private const val KEY_1CB_CLIENT_CONFIG_ID_TOKEN = "smartcredentials_1cb_client_id_token"
        private const val KEY_1CB_CLIENT_CONFIG_ID_RECOMMENDATION =
            "smartcredentials_1cb_client_id_recommendation"
        private const val KEY_IDENTIFIER = "FIREBASE_TOKEN"
        private const val KEY_RECOMMENDATION = "RECOMMENDATION"

    }

    override fun isTokenStorageEmpty(): Boolean = storageTokenList.isEmpty()

    override fun saveToken(token: String) {
        ApiLoggerResolver.logEvent("saveToken: $token")
        try {
            val identifier = JSONObject().put(KEY_IDENTIFIER, token)
            val itemEnvelope =
                ItemEnvelopeFactory.createItemEnvelope(KEY_1CB_CLIENT_CONFIG_ID_TOKEN, identifier)
            mStorageApi.putItem(itemEnvelope, itemContextToken)
            updateTokenList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override val firebaseToken: String
        get() {
            ApiLoggerResolver.logEvent("retrieveToken")
            return storageTokenList[0].identifier.getString(KEY_IDENTIFIER)
        }


    override fun saveRecommendation(recommendation: Recommendation) {
        ApiLoggerResolver.logEvent("saveRecommendation")
        try {
            val identifier = JSONObject().put(KEY_RECOMMENDATION, recommendation.recommendationId)
            val details = JSONObject().put(KEY_RECOMMENDATION, recommendation.clientId)
            val itemEnvelope =
                ItemEnvelopeFactory.createItemEnvelope(
                    KEY_1CB_CLIENT_CONFIG_ID_RECOMMENDATION,
                    identifier,
                    details
                )
            mStorageApi.putItem(itemEnvelope, itemContextRecommendation)
            updateRecommendationList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override val isAnyRecommendationAvailable: Boolean
        get() {
            ApiLoggerResolver.logInfo(
                "isAnyRecommendationAvailable: + ${recommendationList.isNotEmpty()}"
            )
            return recommendationList.isNotEmpty()
        }

    override fun clearRecommendations() {
        ApiLoggerResolver.logEvent("clearRecommendations")
        mStorageApi.deleteItemsByType(mRecommendationFilter)
        updateRecommendationList()
    }

    override val getRecommendation: Recommendation
        get() {
            ApiLoggerResolver.logEvent("retrieveRecommendation")
            val itemEnvelope = recommendationList.last()
            return Recommendation(
                itemEnvelope.identifier.getString(KEY_RECOMMENDATION),
                itemEnvelope.details.getString(KEY_RECOMMENDATION)
            )
        }

    private fun updateTokenList() {
        storageTokenList = mStorageApi.getAllItemsByItemType(mTokenFilter).data
    }

    private fun updateRecommendationList() {
        recommendationList = mStorageApi.getAllItemsByItemType(mRecommendationFilter).data
    }
}