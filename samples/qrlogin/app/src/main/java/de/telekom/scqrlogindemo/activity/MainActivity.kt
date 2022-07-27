package de.telekom.scqrlogindemo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import de.telekom.scqrlogindemo.DemoApplication
import de.telekom.scqrlogindemo.R
import de.telekom.scqrlogindemo.callback.RetrieveConfigurationCallback
import de.telekom.scqrlogindemo.mapper.ConfigurationMapper
import de.telekom.scqrlogindemo.model.UserConfiguration
import de.telekom.scqrlogindemo.rest.RetrofitClient
import de.telekom.scqrlogindemo.rest.http.HttpClientFactory
import de.telekom.scqrlogindemo.rest.model.SignInRequestResponse
import de.telekom.scqrlogindemo.task.SmartTask
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration
import de.telekom.smartcredentials.core.authorization.AuthorizationView
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.core.networking.AuthParamKey
import de.telekom.smartcredentials.core.qrlogin.AuthenticationCallback
import de.telekom.smartcredentials.qrlogin.factory.SmartCredentialsQrLoginFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class MainActivity : AppCompatActivity(), RetrieveConfigurationCallback {
    companion object {
        const val KEY_BARCODE = "key_barcode"
    }

    private lateinit var scannedBarcode: String

    private lateinit var loadingBar: ProgressBar
    private lateinit var loginStatusTextView: TextView
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var retrofitClient: RetrofitClient

    private val cameraLauncherActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent?.getStringExtra(KEY_BARCODE) != null) {
                    Timber.tag(DemoApplication.TAG)
                        .d("QrCode: " + intent.getStringExtra(KEY_BARCODE))
                    scannedBarcode = intent.getStringExtra(KEY_BARCODE)!!
                    val configurationMapper = ConfigurationMapper(this)
                    configurationMapper.retrieveUserConfiguration(this)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()
        retrofitClient = RetrofitClient(HttpClientFactory())

        loadingBar = findViewById(R.id.loading_bar)
        loginStatusTextView = findViewById(R.id.login_status_textview)

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            cameraLauncherActivity.launch(Intent(this@MainActivity, CameraActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.configuration_item) {
            startActivity(Intent(this@MainActivity, UserConfigurationActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeSignInRequest(userConfiguration: UserConfiguration) {
        val credentials = JsonObject()
        credentials.addProperty("username", userConfiguration.username)
        credentials.addProperty("password", userConfiguration.password)

        loginStatusTextView.visibility = View.GONE
        loadingBar.visibility = View.VISIBLE

        val url: String = userConfiguration.url
        if (url.endsWith("/")) {
            if (URLUtil.isValidUrl(url)) {
                compositeDisposable.add(
                    retrofitClient.createRetrofitService(url)
                        .signIn(credentials)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ response ->
                            Timber.tag(DemoApplication.TAG).d(response.toString())
                            authorizeUser(response.body()!!, userConfiguration.webSocket)
                        }, {
                            setLoginStatus("Server Error. Please verify the user configuration.")
                            ApiLoggerResolver.logError(DemoApplication.TAG, it.message)
                        })
                )
            } else {
                setLoginStatus("Server Error. Invalid URL.")
            }
        } else {
            setLoginStatus("Server Error. URL must end in /")
        }
    }

    private fun authorizeUser(reseponse: SignInRequestResponse, webSocketAddress: String) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(AuthParamKey.QR_SERVER_URL.name, webSocketAddress)
            jsonObject.put(AuthParamKey.QR_CODE.name, scannedBarcode)
            jsonObject.put(AuthParamKey.REFRESH_TOKEN.name, reseponse.getRefreshToken())
            jsonObject.put(AuthParamKey.ID_TOKEN.name, reseponse.getIdToken())
            makeAuthorizeApiCall(jsonObject)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun makeAuthorizeApiCall(identifier: JSONObject) {
        val qrLoginApi = SmartCredentialsQrLoginFactory.getQrLoginApi()
        qrLoginApi.authorizeQr(
            this,
            getAuthorizationConfiguration(),
            getAuthorizationCallback(),
            ItemEnvelopeFactory.createItemEnvelope("userConfiguration", identifier)
        )
    }

    private fun getAuthorizationCallback(): AuthenticationCallback {
        return object : AuthenticationCallback() {
            override fun onPluginUnavailable(errorMessage: Any?) {
                Timber.tag(DemoApplication.TAG).d("onPluginUnavailable: $errorMessage")
                setLoginStatus(errorMessage as String)
            }

            override fun onFailed(message: Any?) {
                Timber.tag(DemoApplication.TAG).d("onFailed: $message")
                setLoginStatus(message as String)
            }

            override fun onAuthenticated(authResponse: String?, expirationTime: Long) {
                Timber.tag(DemoApplication.TAG).d("onAuthenticated")
                setLoginStatus("Successful Login!")
            }

            override fun onFailure(error: String?) {
                Timber.tag(DemoApplication.TAG).d("onFailure: $error")
                error?.let { setLoginStatus(it) }
            }
        }
    }

    private fun setLoginStatus(loginStatus: String) {
        SmartTask.with(this)
            ?.assign(getTaskDescription())
            ?.finish(getFinishListener(loginStatus))
            ?.execute()
    }

    private fun getTaskDescription(): SmartTask.TaskDescription {
        return object : SmartTask.TaskDescription {
            override fun onBackground(): Any? {
                return null
            }
        }
    }

    private fun getFinishListener(loginStatus: String): SmartTask.FinishListener {
        return object : SmartTask.FinishListener {
            override fun onFinish(result: Any?) {
                loginStatusTextView.visibility = View.VISIBLE
                loginStatusTextView.text = loginStatus
                loadingBar.visibility = View.GONE
            }
        }
    }

    private fun getAuthorizationConfiguration(): AuthorizationConfiguration {
        val authorizationView: AuthorizationView = AuthorizationView
            .Builder("Confirm QR Login", "Cancel").build()
        return AuthorizationConfiguration
            .Builder(authorizationView)
            .allowDeviceCredentialsFallback(true)
            .build()
    }

    override fun onConfigurationRetrieved(configuration: UserConfiguration?) {
        if (configuration != null) {
            makeSignInRequest(configuration)
        }
    }

    override fun onFailedToRetrieveConfiguration() {
        setLoginStatus("Failed to retrieve the user configuration.")
        Timber.tag(DemoApplication.TAG).d("onFailedToRetrieveConfiguration")
    }
}