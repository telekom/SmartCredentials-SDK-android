package de.telekom.scqrlogindemo.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.telekom.scqrlogindemo.DemoApplication
import de.telekom.scqrlogindemo.R
import de.telekom.scqrlogindemo.task.SmartTask
import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.context.ItemContextFactory
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class UserConfigurationActivity : AppCompatActivity() {
    companion object {
        val ITEM_ID = "user_configuration"
        val ITEM_TYPE = "configuration_type"
        val ITEM_KEY_USERNAME = "configuration_username_key"
        val ITEM_KEY_PASSWORD = "configuration_password_key"
        val ITEM_KEY_URL = "configuration_url_key"
        val ITEM_KEY_WEB_SOCKET = "configuration_web_socket_key"
    }

    private lateinit var storageApi: StorageApi
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var urlEditText: EditText
    private lateinit var webSocketEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_configuration)

        this.supportActionBar?.setTitle(R.string.user_configuration)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        storageApi = SmartCredentialsStorageFactory.getStorageApi()
        usernameEditText = findViewById(R.id.username_edit_text)
        passwordEditText = findViewById(R.id.password_key_edit_text)
        urlEditText = findViewById(R.id.url_key_edit_text)
        webSocketEditText = findViewById(R.id.web_socket_key_edit_text)
        val updateButton = findViewById<FloatingActionButton>(R.id.update_button)
        updateButton.setOnClickListener { view: View? -> updateItem() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        SmartTask.with(this)
            ?.assign(getRetrieveTaskDescription())
            ?.finish(getRetrieveFinishListener())
            ?.execute()
    }

    private fun updateItem() {
        SmartTask.with(this)
            ?.assign(getUpdateTaskDescription())
            ?.finish(getUpdateFinishListener())
            ?.execute()
    }

    private fun getUpdateFinishListener(): SmartTask.FinishListener {
        return object : SmartTask.FinishListener {
            override fun onFinish(result: Any?) {
                finish()
            }
        }
    }

    private fun getUpdateTaskDescription(): SmartTask.TaskDescription {
        return object : SmartTask.TaskDescription {
            override fun onBackground(): Any? {
                val username: String = usernameEditText.getText().toString()
                val password: String = passwordEditText.getText().toString()
                val url: String = urlEditText.getText().toString()
                val webSocket: String = webSocketEditText.getText().toString()
                val identifierJson = JSONObject()
                val detailsJson = JSONObject()
                try {
                    detailsJson.put(ITEM_KEY_USERNAME, username)
                    detailsJson.put(ITEM_KEY_PASSWORD, password)
                    detailsJson.put(ITEM_KEY_URL, url)
                    detailsJson.put(ITEM_KEY_WEB_SOCKET, webSocket)
                    val itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(
                        ITEM_ID,
                        identifierJson,
                        detailsJson
                    )
                    return storageApi.putItem(
                        itemEnvelope,
                        ItemContextFactory.createNonEncryptedNonSensitiveItemContext(ITEM_TYPE)
                    )
                } catch (e: JSONException) {
                    Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.")
                }
                return null
            }
        }
    }

    private fun getRetrieveFinishListener(): SmartTask.FinishListener {
        return object : SmartTask.FinishListener {
            override fun onFinish(result: Any?) {
                val response = result as SmartCredentialsApiResponse<ItemEnvelope>?
                if (response != null && response.isSuccessful) {
                    try {
                        usernameEditText.setText(response.data.details.getString(ITEM_KEY_USERNAME))
                        passwordEditText.setText(response.data.details.getString(ITEM_KEY_PASSWORD))
                        urlEditText.setText(response.data.details.getString(ITEM_KEY_URL))
                        webSocketEditText.setText(
                            response.data.details.getString(
                                ITEM_KEY_WEB_SOCKET
                            )
                        )
                    } catch (e: JSONException) {
                        Timber.tag(DemoApplication.TAG).d("Failed to fetch the user configuration.")
                    }
                }
            }
        }
    }

    private fun getRetrieveTaskDescription(): SmartTask.TaskDescription {
        return object : SmartTask.TaskDescription {
            override fun onBackground(): Any? {
                return storageApi.getItemDetailsById(
                    SmartCredentialsFilterFactory.createNonSensitiveItemFilter(ITEM_ID, ITEM_TYPE)
                )
            }
        }
    }

}