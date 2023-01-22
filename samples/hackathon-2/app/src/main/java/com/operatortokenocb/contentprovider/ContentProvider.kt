package com.operatortokenocb.contentprovider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log

class ContentProvider(private val context: Context) {

    private val URI = "content://de.telekom.entitlements/tokens"
    private val COLUMN_NAME = "transaction"

    fun getTransactionToken(
        bearerToken: String,
        clientId: String,
        scope: String,
        listener: TransactionTokenListener
    ) {
        val CONTENT_URI = Uri.parse(URI)
        val cursor: Cursor? = context.getContentResolver()
            .query(CONTENT_URI, null, null, arrayOf(bearerToken, clientId, scope), null)
        if (cursor == null) {
            listener.onInvalidFetch()
        } else if (!cursor.moveToFirst()) {
            listener.onUnsuccessfulFetch()
        } else {
            val tokenBuilder = StringBuilder()
            while (!cursor.isAfterLast) {
                tokenBuilder.append(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                cursor.moveToNext()
            }
            val transactionToken = tokenBuilder.toString()
            if (transactionToken.isEmpty()) {
                listener.onUnsuccessfulFetch()
            } else {
                Log.d(javaClass.simpleName, tokenBuilder.toString())
                listener.onSuccessfulFetch(tokenBuilder.toString())
            }
        }
        cursor?.close()
    }
}