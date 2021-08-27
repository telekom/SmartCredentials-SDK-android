package de.telekom.scqrlogindemo.mapper

import androidx.appcompat.app.AppCompatActivity
import de.telekom.scqrlogindemo.DemoApplication
import de.telekom.scqrlogindemo.callback.RetrieveConfigurationCallback
import de.telekom.scqrlogindemo.activity.UserConfigurationActivity
import de.telekom.scqrlogindemo.model.UserConfiguration
import de.telekom.scqrlogindemo.task.SmartTask
import de.telekom.smartcredentials.core.api.StorageApi
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory
import org.json.JSONException
import timber.log.Timber

class ConfigurationMapper(private val activity: AppCompatActivity) {

    val storageApi: StorageApi = SmartCredentialsStorageFactory.getStorageApi()

    fun retrieveUserConfiguration(callback: RetrieveConfigurationCallback) {
        SmartTask.with(activity)
            ?.assign(getRetrieveTaskDescription())
            ?.finish(getRetrieveFinishListener(callback))
            ?.execute()
    }

    private fun getRetrieveTaskDescription(): SmartTask.TaskDescription {
        return object : SmartTask.TaskDescription {
            override fun onBackground(): Any? {
                return storageApi.getItemDetailsById(
                    SmartCredentialsFilterFactory.createNonSensitiveItemFilter(
                        UserConfigurationActivity.ITEM_ID,
                        UserConfigurationActivity.ITEM_TYPE
                    )
                )
            }
        }
    }

    private fun getRetrieveFinishListener(callback: RetrieveConfigurationCallback): SmartTask.FinishListener {
        return object : SmartTask.FinishListener {
            override fun onFinish(result: Any?) {
                val response = result as SmartCredentialsApiResponse<ItemEnvelope>?
                if (response != null && response.isSuccessful) {
                    callback.onConfigurationRetrieved(generateUserConfiguration(response.data))
                } else {
                    callback.onFailedToRetrieveConfiguration()
                    Timber.tag(DemoApplication.TAG)
                        .d("Failed to fetch user configuration item details.")
                }
            }
        }
    }

    private fun generateUserConfiguration(itemEnvelope: ItemEnvelope?): UserConfiguration? {
        var userConfiguration: UserConfiguration? = null
        try {
            val itemDetails = itemEnvelope?.details
            userConfiguration = itemDetails?.let {
                UserConfiguration(
                    it.getString(UserConfigurationActivity.ITEM_KEY_USERNAME),
                    it.getString(UserConfigurationActivity.ITEM_KEY_PASSWORD),
                    it.getString(UserConfigurationActivity.ITEM_KEY_URL),
                    it.getString(UserConfigurationActivity.ITEM_KEY_WEB_SOCKET)
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return userConfiguration
    }
}