package com.operatortokenocb.data

import android.content.SharedPreferences
import androidx.core.content.edit

class ContactRepository(
    private val sharedPreferences: SharedPreferences,
) {
    companion object {
        private const val FIRST_NAME = "FIRST_NAME"
        private const val LAST_NAME = "LAST_NAME"
        private const val EMAIL = "EMAIL"
    }

    fun storeInfo(info: Info) {
        sharedPreferences.edit {
            putString(FIRST_NAME, info.firstName)
            putString(LAST_NAME, info.lastName)
            putString(EMAIL, info.email)
        }
    }

    fun getInfo(): Info? {
        val firstName = sharedPreferences.getString(FIRST_NAME, null)
        val lastName = sharedPreferences.getString(LAST_NAME, null)
        val email = sharedPreferences.getString(EMAIL, null)

        if (firstName.isNullOrBlank()) return null
        if (lastName.isNullOrBlank()) return null
        if (email.isNullOrBlank()) return null

        return Info(firstName, lastName, email)
    }
}

data class Info(
    val firstName: String,
    val lastName: String,
    val email: String,
)