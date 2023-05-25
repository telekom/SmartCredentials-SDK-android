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

import java.io.Serializable


class PortalOffer private constructor(builder: PortalOfferBuilder) : Serializable {
    val url: String
    val operatorToken: String
    val firebaseId: String
    val recommendationId: String
    val serverKey: String

    companion object {
        const val PORTAL_OFFER_EXTRA = "extra:portal_offer"
    }

    init {
        url = builder.url
        operatorToken = builder.operatorToken
        firebaseId = builder.firebaseId
        recommendationId = builder.recommendationId
        serverKey = builder.serverKey
    }

    class PortalOfferBuilder {
        internal var url = "https://one-click-portal.onrender.com/"
        internal var operatorToken = ""
        internal var recommendationId = ""
        internal var firebaseId = ""
        internal var serverKey = ""

        fun setOperatorToken(operatorToken: String): PortalOfferBuilder {
            this.operatorToken = operatorToken
            return this
        }

        fun setFirebaseId(firebaseId: String): PortalOfferBuilder {
            this.firebaseId = firebaseId
            return this
        }

        fun setRecommendationId(recommendationId: String): PortalOfferBuilder {
            this.recommendationId = recommendationId
            return this
        }

        fun setServerKey(serverKey: String): PortalOfferBuilder {
            this.serverKey = serverKey
            return this
        }

        fun build(): PortalOffer {
            return PortalOffer(this)
        }
    }


}