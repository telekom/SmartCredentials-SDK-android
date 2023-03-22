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
package de.telekom.smartcredentials.oneclickbusinessclient.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import de.telekom.smartcredentials.oneclickbusinessclient.R
import de.telekom.smartcredentials.oneclickbusinessclient.ui.PortalOffer
import de.telekom.smartcredentials.oneclickbusinessclient.ui.ui.theme.MagentaColorPrimary
import de.telekom.smartcredentials.oneclickbusinessclient.ui.webViews.TopBar
import de.telekom.smartcredentials.oneclickbusinessclient.ui.webViews.WebViewContent

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
class PortalActivity : ComponentActivity() {

    companion object {
        val TRANSACTION_TOKEN_QUERY = "token="
        val FIREBASE_ID_QUERY = "firebaseId="
        val RECOMMENDATION_ID_QUERY = "recommendationId="
        val SERVER_KEY_QUERY = "serverKey="
        val DEBUG_QUERY = "debug="
        val DEBUG = "true"
        val finalUrl = mutableStateOf("")

        fun newIntent(activity: Activity, offer: PortalOffer): Intent {
            val intent = Intent(activity, PortalActivity::class.java)
            intent.putExtra(PortalOffer.PORTAL_OFFER_EXTRA, offer)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = MagentaColorPrimary.toArgb()
            MainContent()

            val portalOffer =
                intent.getSerializableExtra(PortalOffer.PORTAL_OFFER_EXTRA) as PortalOffer
            finalUrl(
                url = portalOffer.getUrl(),
                transactionToken = portalOffer.getTransactionToken(),
                firebaseId = portalOffer.getFirebaseId(),
                recommendationId = portalOffer.getRecommendationId(),
                serverKey = portalOffer.getServerKey()
            )
        }
    }

    @Composable
    fun MainContent() {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(id = R.string.one_click_portal).uppercase(),
                    backgroundColor = MagentaColorPrimary,
                    onClose = { finish() }
                )
            },
            content = {
                WebViewContent(finalUrl)
            }
        )
    }

    private fun finalUrl(
        url: String,
        transactionToken: String,
        firebaseId: String,
        recommendationId: String,
        serverKey: String
    ) {
        finalUrl.value = url + "?" +
                TRANSACTION_TOKEN_QUERY +
                transactionToken + "&" +
                FIREBASE_ID_QUERY +
                firebaseId + "&" +
                RECOMMENDATION_ID_QUERY +
                recommendationId + "&" +
                SERVER_KEY_QUERY +
                serverKey + "&" +
                DEBUG_QUERY +
                DEBUG
    }
}
