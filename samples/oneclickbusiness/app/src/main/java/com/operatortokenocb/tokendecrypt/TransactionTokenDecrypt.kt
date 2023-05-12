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

package com.operatortokenocb.tokendecrypt

import android.content.Context
import android.util.Base64
import com.operatortokenocb.R
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class TransactionTokenDecrypt(
    private val context: Context
) {

    /*
         RSA Private Key is fetched from the raw/assets folder of the project
         only for the hackathon purpose. In a normal flow, the decryption
         should be done on a client server which corresponds to the client id
         the token was created for, without storing secrets inside the mobile app.
    */

    private val TAG = "transaction_token_decrypt"

    fun getClaimsFromTransactionToken(
        transactionToken: String?
    ): String {
        val privateKey = privateKeyFromString()

        val decodedBytes: ByteArray = Base64.decode(transactionToken, Base64.DEFAULT)
        val decodedToken = String(decodedBytes)

        val jo = JSONObject(decodedToken)
        val jaData: JSONArray = jo["recipients"] as JSONArray
        val jo1 = jaData[0] as JSONObject

        val recipientEncryptedKey: ByteArray = Base64.decode(
            jo1["encrypted_key"].toString(), Base64.DEFAULT
        )

        val decryptCipher: Cipher =
            Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding")

        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey)
        val contentEncryptionKey: ByteArray =
            decryptCipher.doFinal(recipientEncryptedKey)


        val decodedIv: ByteArray = Base64.decode(jo["iv"].toString(), Base64.DEFAULT)
        val decodedTag: ByteArray = Base64.decode(jo["tag"].toString(), Base64.DEFAULT)
        val decodedCipherText: ByteArray = Base64.decode(
            jo["ciphertext"].toString(), Base64.DEFAULT
        )
        val cipher: Cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keySpec = SecretKeySpec(contentEncryptionKey, "AES")
        val gcmParameterSpec = GCMParameterSpec(decodedTag.size * 8, decodedIv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)
        val aad: ByteArray = jo.getString("protected").toString().toByteArray()
        cipher.updateAAD(aad)
        cipher.update(decodedCipherText)
        val decryptedText: ByteArray = cipher.doFinal(decodedTag)

        val claims = String(decryptedText, StandardCharsets.UTF_8)
        Timber.tag(TAG).d("Claims :%s", claims)
        return claims
    }

    private fun privateKeyFromString(): PrivateKey {
        val key = readTextFile(context.resources.openRawResource(R.raw.tphonehack_private_key))

        val publicKey = key.replace("\\n", "")
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "")

        val decoded = Base64.decode(publicKey, Base64.DEFAULT)
        val keySpecPKCS8 = PKCS8EncodedKeySpec(decoded)
        val kf = KeyFactory.getInstance("RSA")
        return kf.generatePrivate(keySpecPKCS8)
    }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }
}