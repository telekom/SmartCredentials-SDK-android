package com.operatortokenocb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.operatortokenocb.R
import com.operatortokenocb.contentprovider.ContentProvider
import com.operatortokenocb.contentprovider.TransactionTokenDecrypt
import com.operatortokenocb.contentprovider.TransactionTokenListener
import com.operatortokenocb.databinding.FragmentTokenFetchBinding
import com.operatortokenocb.network.BaseRetrofitClient
import com.operatortokenocb.network.GetBearerBody
import com.operatortokenocb.network.PartnerManagementApi
import com.operatortokenocb.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import timber.log.Timber

class TokenFetchFragment : Fragment(), TransactionTokenListener {
    private val TAG = "token_fetch_fragment"

    private lateinit var binding: FragmentTokenFetchBinding
    private lateinit var contentProvider: ContentProvider
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var transactionTokenDecrypt: TransactionTokenDecrypt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTokenFetchBinding.inflate(layoutInflater)
        contentProvider = ContentProvider(requireContext())
        transactionTokenDecrypt = TransactionTokenDecrypt(requireContext())
        compositeDisposable = CompositeDisposable()
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
        val retrofitClient: Retrofit =
            RetrofitClient().createRetrofitClient(BaseRetrofitClient.PARTNER_APPLICATION_URL)
        val api: PartnerManagementApi = retrofitClient.create(PartnerManagementApi::class.java)

        compositeDisposable.add(api.observeAccessToken("Hackaton-Sample-App-0ae7264a-0f3d-4859-a9aa-97788446e9e2")
            .flatMap { accessToken ->
                val body = GetBearerBody(accessToken, null, requireActivity().packageName)
                api.observeBearerToken(body)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ bearerToken ->
                contentProvider.getTransactionToken(
                    bearerToken,
                    "tphonehack",
                    scope,
                    this
                )
            }
            ) { throwable ->
                Timber.tag(TAG).e(throwable.stackTraceToString())
            })
    }

    override fun onSuccessfulFetch(token: String?) {
        val data = transactionTokenDecrypt.getClaimsFromTransactionToken(token.toString())
        binding.operatorTokenDescriptionTextView.text = data
        Timber.tag(TAG).d(token.toString())
    }

    override fun onUnsuccessfulFetch() {
        binding.operatorTokenDescriptionTextView.text =
            resources.getString(R.string.unsuccessful_fetch)
    }

    override fun onInvalidFetch() {
        binding.operatorTokenDescriptionTextView.text =
            resources.getString(R.string.invalid_fetch)
    }
}

