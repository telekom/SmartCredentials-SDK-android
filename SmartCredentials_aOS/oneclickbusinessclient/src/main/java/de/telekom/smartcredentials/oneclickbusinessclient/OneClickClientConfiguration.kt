package de.telekom.smartcredentials.oneclickbusinessclient

import java.util.*

class OneClickClientConfiguration(builder: Builder) {

    private var mServerUrl: String
    private var mFirebaseServerKey: String
    private var mClientId: String
    private var mScope: String
    
    init {
        mServerUrl = builder.serverUrl
        mFirebaseServerKey = builder.firebaseServerKey
        mClientId = builder.clientId
        mScope = builder.scope
    }

    class Builder {
        internal var serverUrl: String = ""
        internal var firebaseServerKey: String = ""
        internal var clientId: String = ""
        internal var scope: String = ""

        init {
            Objects.requireNonNull(serverUrl, NULL_SERVER_URL)
            Objects.requireNonNull(firebaseServerKey, NULL_FIREBASE_SERVER_KEY)
            Objects.requireNonNull(clientId, NULL_CLIENT_ID)
            Objects.requireNonNull(scope, NULL_SCOPE)
        }

        fun setServerUrl(url: String): Builder {
            this.serverUrl = url
            return this
        }


        fun setFirebaseServerKey(key: String): Builder {
            this.firebaseServerKey = key
            return this
        }

        fun setClientId(clientId: String): Builder {
            this.clientId = clientId
            return this
        }

        fun setScope(scope: String): Builder {
            this.scope = scope
            return this
        }

        fun build(): OneClickClientConfiguration {
            return OneClickClientConfiguration(this)
        }

        companion object {
            const val NULL_SERVER_URL = "Server URL must not be null."
            const val NULL_FIREBASE_SERVER_KEY = "Firebase server key must not be null."
            const val NULL_CLIENT_ID = "Client ID must not be null."
            const val NULL_SCOPE = "Scope must not be null."
        }
    }

    fun getServerUrl(): String = mServerUrl

    fun getFirebaseServerKey() = mFirebaseServerKey

    fun getClientId() = mClientId

    fun getScope() = mScope

}