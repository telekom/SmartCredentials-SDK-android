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
package de.telekom.smartcredentials.oneclickbusinessclient.controllers

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.api.OneClickApi
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet
import de.telekom.smartcredentials.core.controllers.CoreController
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable
import de.telekom.smartcredentials.core.responses.RootedThrowable
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import de.telekom.smartcredentials.oneclickbusinessclient.recommendation.Recommendation
import de.telekom.smartcredentials.oneclickbusinessclient.recommendation.RecommendationConstants
import de.telekom.smartcredentials.oneclickbusinessclient.repository.StorageRepo
import de.telekom.smartcredentials.oneclickbusinessclient.rest.MakeRecommendationsBody
import de.telekom.smartcredentials.oneclickbusinessclient.rest.RecommenderApi
import de.telekom.smartcredentials.oneclickbusinessclient.rest.RetrofitClient
import de.telekom.smartcredentials.oneclickbusinessclient.ui.PortalOffer
import de.telekom.smartcredentials.oneclickbusinessclient.ui.UIHandler
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModel
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModelFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
class OneClickBusinessClientController(
    private val mContext: Context,
    private val mCoreController: CoreController,
    private val mIdentityProviderApi: IdentityProviderApi,
    private val mStorageRepository: StorageRepo,
    private val config: OneClickClientConfiguration
) : OneClickApi {

    private var oneClickListener: OneClickListener

    internal fun getOperatorToken(): SmartCredentialsApiResponse<String> =
        mIdentityProviderApi.getOperatorToken(mContext, config.getClientId(), config.getScope())

    init {
        oneClickListener = UIHandler(this)
    }

    @Deprecated("Deprecated in Java")
    override fun makeRecommendation(
        productIds: List<String>
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "makeRecommendation")
        if (mCoreController.isSecurityCompromised) {
            mCoreController.handleSecurityCompromised()
            throw Exception(RootedThrowable())
        }
        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.ONE_CLICK_CLIENT)) {
            val errorMessage = SmartCredentialsFeatureSet.ONE_CLICK_CLIENT.notSupportedDesc
            throw Exception(FeatureNotSupportedThrowable(errorMessage))
        }

        if (mStorageRepository.isTokenStorageEmpty()) {
            throw RuntimeException("Firebase token is null")
        }

        val retrofit = RetrofitClient().createRetrofitClient(config.getServerUrl())
        val recommenderApi = retrofit.create(RecommenderApi::class.java)
        try {
            val token = mStorageRepository.firebaseToken
            Observable.just(token)
                .flatMap { firebaseToken: String ->
                    val body = MakeRecommendationsBody(
                        firebaseToken,
                        productIds,
                        config.getFirebaseServerKey()
                    )
                    recommenderApi.observeMakeRecommendations(body)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    override fun pushRecommendationMessage(
        data: Map<String, String>
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "pushRecommendationMessage")
        if (mCoreController.isSecurityCompromised) {
            mCoreController.handleSecurityCompromised()
            throw Exception(RootedThrowable())
        }
        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.ONE_CLICK_CLIENT)) {
            val errorMessage = SmartCredentialsFeatureSet.ONE_CLICK_CLIENT.notSupportedDesc
            throw Exception(FeatureNotSupportedThrowable(errorMessage))
        }

        val recommendationId: String =
            data[RecommendationConstants.KEY_RECOMMENDATION_ID].toString()
        val clientId: String = data[RecommendationConstants.KEY_CLIENT_ID].toString()
        ApiLoggerResolver.logEvent(
            "Received recommendation notification with ID " + recommendationId + "and client ID " + clientId
        )
        val recommendation = Recommendation(recommendationId, clientId)
        mStorageRepository.saveRecommendation(recommendation)
        ApiLoggerResolver.logEvent("pushRecommendationMessage: clientId = $clientId and recommendationId = $recommendationId")
        oneClickListener.onRecommendationReceived()
    }

    override fun updateFirebaseToken(
        token: String
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "updateFirebaseToken")
        if (mCoreController.isSecurityCompromised) {
            mCoreController.handleSecurityCompromised()
            throw Exception(RootedThrowable())
        }
        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.ONE_CLICK_CLIENT)) {
            val errorMessage = SmartCredentialsFeatureSet.ONE_CLICK_CLIENT.notSupportedDesc
            throw Exception(FeatureNotSupportedThrowable(errorMessage))
        }
        mStorageRepository.saveToken(token)
    }


    override fun createPortalOffer(
        token: String
    ) {
        ApiLoggerResolver.logMethodAccess(javaClass.simpleName, "createPortalOffer")
        if (mCoreController.isSecurityCompromised) {
            mCoreController.handleSecurityCompromised()
            throw Exception(RootedThrowable())
        }
        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.ONE_CLICK_CLIENT)) {
            val errorMessage = SmartCredentialsFeatureSet.ONE_CLICK_CLIENT.notSupportedDesc
            throw Exception(FeatureNotSupportedThrowable(errorMessage))
        }

        if (mStorageRepository.isAnyRecommendationAvailable) {
            ApiLoggerResolver.logInfo("Should open portal on file")
            val recommendation = mStorageRepository.getRecommendation
            mStorageRepository.clearRecommendations()
            try {
                val firebaseToken = mStorageRepository.firebaseToken
                val portalOffer = recommendation?.recommendationId?.let { recommendationId ->

                    PortalOffer.PortalOfferBuilder()
                        .setTransactionToken(token)
                        .setFirebaseId(firebaseToken)
                        .setRecommendationId(recommendationId)
                        .setServerKey(config.getFirebaseServerKey())
                        .build()
                }
                portalOffer?.let { offer -> oneClickListener.onOfferAvailable(offer) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            ApiLoggerResolver.logError(javaClass.name, "Unknown event")
        }
    }
}
