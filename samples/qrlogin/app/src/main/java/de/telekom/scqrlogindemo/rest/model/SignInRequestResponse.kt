package de.telekom.scqrlogindemo.rest.model

import com.google.gson.annotations.SerializedName

class SignInRequestResponse {

    @SerializedName("access_token")
    var mAccessToken: String? = null

    @SerializedName("refresh_token")
    var mRefreshToken: String? = null

    @SerializedName("id_token")
    var mIdToken: String? = null

    @SerializedName("scope")
    var mScope: String? = null

    @SerializedName("expires_in")
    var mExpireTime = 0

    @SerializedName("token_type")
    var mTokenType: String? = null

    fun getAccessToken(): String? {
        return mAccessToken
    }

    fun getRefreshToken(): String? {
        return mRefreshToken
    }

    fun getIdToken(): String? {
        return mIdToken
    }

    fun getScope(): String? {
        return mScope
    }

    fun getExpireTime(): Int {
        return mExpireTime
    }

    fun getTokenType(): String? {
        return mTokenType
    }

    override fun toString(): String {
        return "SignInRequestResponse(mAccessToken=$mAccessToken, mRefreshToken=$mRefreshToken, mIdToken=$mIdToken, mScope=$mScope, mExpireTime=$mExpireTime, mTokenType=$mTokenType)"
    }


}