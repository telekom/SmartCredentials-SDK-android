package de.telekom.scauthorizationdemo.mappers;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import de.telekom.scauthorizationdemo.DemoApplication;
import de.telekom.scauthorizationdemo.activities.ConfigurationActivity;
import de.telekom.scauthorizationdemo.callbacks.RetrieveConfigurationCallback;
import de.telekom.scauthorizationdemo.task.SmartTask;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;
import de.telekom.smartcredentials.core.authorization.AuthorizationView;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;


public class ConfigurationMapper {

    private final AppCompatActivity activity;
    private final StorageApi storageApi;

    public ConfigurationMapper(AppCompatActivity activity) {
        this.activity = activity;
        this.storageApi = SmartCredentialsStorageFactory.getStorageApi();
    }

    public void retrieveAuthorizationConfiguration(RetrieveConfigurationCallback callback) {
        SmartTask.with(activity)
                .assign(() -> storageApi.getItemDetailsById(SmartCredentialsFilterFactory.createNonSensitiveItemFilter(
                        ConfigurationActivity.ITEM_ID,
                        ConfigurationActivity.ITEM_TYPE)))
                .finish(result -> {
                    SmartCredentialsApiResponse<ItemEnvelope> response =
                            (SmartCredentialsApiResponse<ItemEnvelope>) result;
                    if (response != null && response.isSuccessful()) {
                        callback.onConfigurationRetrieved(generateConfiguration(response.getData()));
                    } else {
                        callback.onFailedToRetrieveConfiguration();
                        Timber.tag(DemoApplication.TAG).d("Failed to fetch item details.");
                    }
                })
                .execute();
    }

    private AuthorizationConfiguration generateConfiguration(ItemEnvelope itemEnvelope) {
        AuthorizationConfiguration authorizationConfiguration = null;
        try {
            AuthorizationView.Builder authorizationViewBuilder = new AuthorizationView.Builder(
                    itemEnvelope.getDetails().getString(ConfigurationActivity.ITEM_KEY_TITLE),
                    itemEnvelope.getDetails().getString(ConfigurationActivity.ITEM_KEY_NEGATIVE_BUTTON_TEXT));

            String subtitle = itemEnvelope.getDetails().getString(ConfigurationActivity.ITEM_KEY_SUBTITLE);
            if (!subtitle.isEmpty()) {
                authorizationViewBuilder.setSubtitle(subtitle);
            }

            String description = itemEnvelope.getDetails().getString(ConfigurationActivity.ITEM_KEY_DESCRIPTION);
            if (!description.isEmpty()) {
                authorizationViewBuilder.setDescription(description);
            }

            AuthorizationConfiguration.Builder authorizationConfigurationBuilder = new AuthorizationConfiguration
                    .Builder(authorizationViewBuilder.build());
            authorizationConfigurationBuilder.allowDeviceCredentialsFallback(
                    itemEnvelope.getDetails().getBoolean(ConfigurationActivity.ITEM_KEY_DEVICE_CREDENTIALS_FALLBACK));
            authorizationConfigurationBuilder.requireFaceRecognitionConfirmation(
                    itemEnvelope.getDetails().getBoolean(ConfigurationActivity.ITEM_KEY_REQUIRE_CONFIRMATION));
            authorizationConfiguration = authorizationConfigurationBuilder.build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return authorizationConfiguration;
    }
}
