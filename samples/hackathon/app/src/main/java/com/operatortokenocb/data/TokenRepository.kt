package com.operatortokenocb.data

import android.content.SharedPreferences
import androidx.core.content.edit

class TokenRepository(
    private val sharedPreferences: SharedPreferences,
) {
    companion object {
        private const val KEY = "com.operatortokenocb.data.token"
    }

    fun getToken(): String? = sharedPreferences.getString(KEY, null)

    fun storeToken(token: String) {
        sharedPreferences.edit {
            putString(KEY, token)
        }
    }
}