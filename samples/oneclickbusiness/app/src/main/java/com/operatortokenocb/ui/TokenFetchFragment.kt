package com.operatortokenocb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.operatortokenocb.OcbApplication
import com.operatortokenocb.databinding.FragmentTokenFetchBinding
import com.operatortokenocb.tokendecrypt.TransactionTokenDecrypt
import de.telekom.identityprovider.factory.SmartCredentialsIdentityProviderFactory
import de.telekom.smartcredentials.core.api.IdentityProviderApi
import retrofit2.HttpException
import timber.log.Timber

class TokenFetchFragment : Fragment() {
    private val TAG = "token_fetch_fragment"

    private lateinit var binding: FragmentTokenFetchBinding
    private lateinit var transactionTokenDecrypt: TransactionTokenDecrypt

    companion object {
        const val PARTNER_APPLICATION_URL = "https://lbl-partmgmr.superdtaglb.cf/"
        const val CREDENTIALS = "Moonlight-017e56d0-7997-44da-bac6-a3c3f4a42bea"
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
        val data = identityProviderApi.getOperatorToken(
            requireContext(),
            PARTNER_APPLICATION_URL,
            CREDENTIALS,
            "oneclickdemo",
            scope
        )
        if (data.isSuccessful) {
            binding.operatorTokenDescriptionTextView.text = data.data
        } else {
            if (data.error is HttpException) {
                Timber.tag(TAG).e((data.error as HttpException).response()!!.errorBody()!!.string())
            } else {
                Timber.tag(TAG).e(data.error.toString())
            }
        }
    }
}

