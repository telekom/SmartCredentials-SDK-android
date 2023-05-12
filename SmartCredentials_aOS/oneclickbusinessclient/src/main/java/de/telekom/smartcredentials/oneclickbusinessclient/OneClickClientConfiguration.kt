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

package de.telekom.smartcredentials.oneclickbusinessclient

import java.lang.IllegalArgumentException
import java.util.*

class OneClickClientConfiguration(builder: Builder) {

    var serverUrl: String
        private set
    var firebaseServerKey: String
        private set
    var credentials: String
        private set
    var logoResId: Int
        private set
    var clientAppName: String
        private set

    init {
        serverUrl = builder.serverUrl!!
        firebaseServerKey = builder.firebaseServerKey!!
        credentials = builder.credentials!!
        logoResId = builder.logoResId!!
        clientAppName = builder.clientAppName!!
    }

    class Builder {
        internal var serverUrl: String? = null
        internal var firebaseServerKey: String? = null
        internal var credentials: String? = null
        internal var logoResId: Int? = null
        internal var clientAppName: String? = null

        fun setServerUrl(url: String): Builder {
            this.serverUrl = url
            return this
        }

        fun setFirebaseServerKey(key: String): Builder {
            this.firebaseServerKey = key
            return this
        }

        fun setCredentials(credentials: String): Builder {
            this.credentials = credentials
            return this
        }

        fun setLogoResId(logoResId: Int): Builder {
            this.logoResId = logoResId
            return this
        }

        fun setClientAppName(clientAppName: String): Builder {
            this.clientAppName = clientAppName
            return this
        }

        fun build(): OneClickClientConfiguration {
            if(serverUrl == null) {
                throw IllegalArgumentException("Server URL must not be null")
            }
            if(firebaseServerKey == null) {
                throw IllegalArgumentException("Firebase Server Key must not be null")
            }
            if(credentials == null) {
                throw IllegalArgumentException("Credentials must not be null")
            }
            if(logoResId == null) {
                throw IllegalArgumentException("Logo resId must not be null")
            }
            if(clientAppName == null) {
                throw IllegalArgumentException("Client app name must not be null")
            }
            return OneClickClientConfiguration(this)
        }
    }
}