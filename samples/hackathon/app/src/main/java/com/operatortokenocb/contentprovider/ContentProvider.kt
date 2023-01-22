package com.operatortokenocb.contentprovider

import android.content.Context
import android.database.Cursor
import android.net.Uri
import io.reactivex.Observable
import timber.log.Timber

class ContentProvider(private val context: Context) {

    private val URI = "content://de.telekom.entitlements/tokens"
    private val COLUMN_NAME = "transaction"

    fun getTransactionToken(
        bearerToken: String,
        clientId: String,
        scope: String,
    ): Observable<String> {
        return Observable.create { emitter ->
            val CONTENT_URI = Uri.parse(URI)
            val cursor: Cursor? = context.contentResolver
                .query(CONTENT_URI, null, null, arrayOf(bearerToken, clientId, scope), null)
            if (cursor == null) {
                emitter.onError(InvalidFetchError())
                emitter.onComplete()
            } else if (!cursor.moveToFirst()) {
                emitter.onError(UnsuccessfulError())
                emitter.onComplete()
            } else {
                val tokenBuilder = StringBuilder()
                while (!cursor.isAfterLast) {
                    tokenBuilder.append(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)))
                    cursor.moveToNext()
                }
                val transactionToken = tokenBuilder.toString()
                if (transactionToken.isEmpty()) {
                    emitter.onError(UnsuccessfulError())
                    emitter.onComplete()
                } else {
                    Timber.tag(javaClass.simpleName).d(tokenBuilder.toString())
                    emitter.onNext(tokenBuilder.toString())
                    emitter.onComplete()
                }
            }
            cursor?.close()
        }
    }
}

class InvalidFetchError : Throwable()

class UnsuccessfulError : Throwable()
