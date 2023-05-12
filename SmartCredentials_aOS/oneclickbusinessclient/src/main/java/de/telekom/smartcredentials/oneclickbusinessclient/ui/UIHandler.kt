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

package de.telekom.smartcredentials.oneclickbusinessclient.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.oneclickbusinessclient.OneClickClientConfiguration
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController
import de.telekom.smartcredentials.oneclickbusinessclient.di.ObjectGraphCreatorOneClickBusinessClient
import de.telekom.smartcredentials.oneclickbusinessclient.ui.activity.PortalActivity

class UIHandler(
    private val oneClickBusinessClientController: OneClickBusinessClientController,
    private val config: OneClickClientConfiguration
) {

    private var mActivity: AppCompatActivity? = null
    private var mComposeView: ComposeView? = null
    private val recommendationFlowRunning = mutableStateOf(false)

    fun bind(
        activity: AppCompatActivity
    ) {
        mActivity = activity
    }

    fun setComposeView(
        composeView: ComposeView?
    ) {
        mComposeView = composeView
    }

    fun unbind() {
        mActivity = null
        mComposeView = null
    }

    private fun setupComposeView() {
        if (mActivity != null) {
            mComposeView?.setContent {
                OneClickScreen(
                    ObjectGraphCreatorOneClickBusinessClient
                        .provideViewModelOneClickClient(),
                    config,
                    showComposeFlow = recommendationFlowRunning.value,
                    signalFlowEnd = {
                        recommendationFlowRunning.value = false
                    },
                    signalTokenReceived = { tokenResponse ->
                        recommendationFlowRunning.value = false
                        oneClickBusinessClientController.createPortalOffer(tokenResponse.data)
                    }
                )
            }
        } else {
            ApiLoggerResolver.logError(javaClass.name, "No activity bind")
        }
    }

    fun onRecommendationReceived() {
        recommendationFlowRunning.value = true
        setupComposeView()
    }

    fun onOfferAvailable(offer: PortalOffer) {
        mActivity?.let { activity ->
            activity.startActivity(PortalActivity.newIntent(activity, offer))
        }
    }
}