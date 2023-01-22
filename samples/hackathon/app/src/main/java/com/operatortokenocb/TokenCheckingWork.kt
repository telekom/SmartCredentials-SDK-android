package com.operatortokenocb

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.operatortokenocb.contentprovider.ContentProvider
import com.operatortokenocb.contentprovider.TransactionTokenDecrypt
import com.operatortokenocb.data.AlertApi
import com.operatortokenocb.data.AlertRepository
import com.operatortokenocb.data.ContactRepository
import com.operatortokenocb.data.TokenRepository
import com.operatortokenocb.network.BaseRetrofitClient
import com.operatortokenocb.network.GetBearerBody
import com.operatortokenocb.network.PartnerManagementApi
import com.operatortokenocb.network.RetrofitClient
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.create
import timber.log.Timber

class TokenCheckingWork(
    appContext: Context,
    workerParams: WorkerParameters,
) : RxWorker(appContext, workerParams) {
    override fun createWork(): Single<Result> {
        Timber.tag("TCW").d("work running")

        val api = getTokenApi()
        return api.observeAccessToken("Hackaton-Sample-App-0ae7264a-0f3d-4859-a9aa-97788446e9e2")
            .flatMap { accessToken ->
                val body = GetBearerBody(accessToken, null, applicationContext.packageName)
                api.observeBearerToken(body)
            }
            .flatMap { bearerToken ->
                ContentProvider(applicationContext)
                    .getTransactionToken(bearerToken, "tphonehack", "psi")
            }
            .map { token ->
                val data = TransactionTokenDecrypt(applicationContext).getClaimsFromTransactionToken(token)
                Timber.tag("TCW").d(data)

                val sharePref = applicationContext.getSharedPreferences("fuck", Context.MODE_PRIVATE)
                val tokenRepo = TokenRepository(sharePref)
                val contactRepo = ContactRepository(sharePref)
                val email = contactRepo.getInfo()?.email

                val oldToken = tokenRepo.getToken()
                if (oldToken.isNullOrBlank()) {
                    tokenRepo.storeToken(data)
                } else if (oldToken == data) {
                    Timber.tag("TCW").d("same device")
                } else {
//                    AlertRepository(getAlertApi())
//                        .sendAlert()
                    Timber.tag("TCW").d("TODO")
                }

                data
            }
            .singleOrError()
            .map {
                when {
                    it.isBlank() -> Result.failure()
                    else -> Result.success()
                }
            }

    }

    private fun getTokenApi(): PartnerManagementApi {
        val retrofitClient: Retrofit =
            RetrofitClient().createRetrofitClient(BaseRetrofitClient.PARTNER_APPLICATION_URL)
        return retrofitClient.create(PartnerManagementApi::class.java)
    }

    private fun getAlertApi(): AlertApi {
        val retrofitClient: Retrofit =
            RetrofitClient().createRetrofitClient(TODO("our fake server url"))

        return retrofitClient.create()
    }
}