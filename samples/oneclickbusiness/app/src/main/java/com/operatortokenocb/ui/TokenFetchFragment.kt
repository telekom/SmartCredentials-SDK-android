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

package com.operatortokenocb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.operatortokenocb.BuildConfig
import com.operatortokenocb.databinding.FragmentTokenFetchBinding
import com.operatortokenocb.tokendecrypt.TransactionTokenDecrypt
import com.operatortokenocb.ui.TokenFetchFragment.Companion.CLIENT_ID
import de.telekom.identityprovider.factory.SmartCredentialsIdentityProviderFactory
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import de.telekom.smartcredentials.core.identityprovider.IdentityProviderCallback
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import retrofit2.HttpException
import timber.log.Timber

class TokenFetchFragment : Fragment(), IdentityProviderCallback {
    private val TAG = "token_fetch_fragment"

    private lateinit var binding: FragmentTokenFetchBinding
    private lateinit var transactionTokenDecrypt: TransactionTokenDecrypt

    companion object {
        const val PARTNER_APPLICATION_URL = BuildConfig.PARTNER_BE_URL
        const val CREDENTIALS = BuildConfig.PARTNER_CREDENTIALS
        const val CLIENT_ID = BuildConfig.CLIENT_ID
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTokenFetchBinding.inflate(layoutInflater)
        transactionTokenDecrypt = TransactionTokenDecrypt(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ocbOperatorTokenButton.setOnClickListener {
            getToken("imsi")
        }

        binding.ocbPidTokenButton.setOnClickListener {
            getToken("psi")
        }
    }

    private fun getToken(scope: String) {
        val identityProviderApi: IdentityProviderApi =
            SmartCredentialsIdentityProviderFactory.identityProviderApi
        identityProviderApi.getOperatorToken(
            requireContext(),
            PARTNER_APPLICATION_URL,
            CREDENTIALS,
            CLIENT_ID,
            scope,
            this
        )
    }

    override fun onResult(result: SmartCredentialsApiResponse<String>?) {
        if (result!!.isSuccessful) {
            binding.operatorTokenDescriptionTextView.text = result.data
        } else {
            if (result.error is HttpException) {
                Timber.tag(TAG)
                    .e((result.error as HttpException).response()!!.errorBody()!!.string())
            } else {
                Timber.tag(TAG).e(result.error.toString())
            }
        }
    }
}

