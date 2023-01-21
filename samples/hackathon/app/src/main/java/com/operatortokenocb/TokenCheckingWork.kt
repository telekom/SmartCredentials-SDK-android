package com.operatortokenocb

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.operatortokenocb.contentprovider.ContentProvider
import com.operatortokenocb.contentprovider.TransactionTokenDecrypt
import com.operatortokenocb.network.BaseRetrofitClient
import com.operatortokenocb.network.GetBearerBody
import com.operatortokenocb.network.PartnerManagementApi
import com.operatortokenocb.network.RetrofitClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
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

                data
            }
            .singleOrError()
            .map {
                when {
                    it.isNullOrBlank() -> Result.failure()
                    else -> Result.success()
                }
            }

    }

    private fun getTokenApi(): PartnerManagementApi {
        val retrofitClient: Retrofit =
            RetrofitClient().createRetrofitClient(BaseRetrofitClient.PARTNER_APPLICATION_URL)
        return retrofitClient.create(PartnerManagementApi::class.java)
    }

}