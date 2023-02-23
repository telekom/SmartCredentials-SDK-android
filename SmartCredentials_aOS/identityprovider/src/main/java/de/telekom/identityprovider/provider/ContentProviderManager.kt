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
package de.telekom.identityprovider.provider

import android.content.Context
import android.net.Uri

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
class ContentProviderManager(private val context: Context) {

    @Throws(Exception::class)
    fun getOperatorToken(bearerToken: String, clientId: String, scope: String): String {
        val CONTENT_URI = Uri.parse(URI)
        val cursor = context.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            arrayOf(bearerToken, clientId, scope),
            null
        )
        return if (cursor == null) {
            throw Exception("Invalid getOperatorToken fetch")
        } else if (!cursor.moveToFirst()) {
            throw Exception("Unsuccessful getOperatorToken fetch")
        } else {
            val tokenBuilder = StringBuilder()
            while (!cursor.isAfterLast) {
                tokenBuilder.append(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
                cursor.moveToNext()
            }
            if (tokenBuilder.toString().isEmpty()) {
                throw Exception("Unsuccessful getOperatorToken fetch")
            } else {
                cursor.close()
                tokenBuilder.toString()
            }
        }
    }

    companion object {
        const val URI = "content://de.telekom.entitlements/tokens"
        const val COLUMN_NAME = "transaction"
    }
}