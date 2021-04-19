package de.telekom.scauthorizationdemo.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import de.telekom.scauthorizationdemo.DemoApplication;
import de.telekom.scauthorizationdemo.R;
import de.telekom.scauthorizationdemo.task.SmartTask;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public class ConfigurationActivity extends AppCompatActivity{

    public static final String ITEM_ID = "authorization_configuration";
    public static final String ITEM_TYPE = "configuration_type";

    public static final String ITEM_KEY_TITLE = "configuration_title_key";
    public static final String ITEM_KEY_SUBTITLE = "configuration_subtitle_key";
    public static final String ITEM_KEY_DESCRIPTION = "configuration_description_key";
    public static final String ITEM_KEY_NEGATIVE_BUTTON_TEXT = "configuration_negative_button_key";
    public static final String ITEM_KEY_DEVICE_CREDENTIALS_FALLBACK = "configuration_device_credentials_fallback_key";
    public static final String ITEM_KEY_REQUIRE_CONFIRMATION = "configuration_require_confirmation_button_key";

    private StorageApi storageApi;
    private EditText titleEditText;
    private EditText subtitleEditText;
    private EditText descriptionEditText;
    private EditText negativeButtonEditText;
    private SwitchMaterial deviceCredentialsSwitch;
    private SwitchMaterial requireConfirmationSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Objects.requireNonNull(this.getSupportActionBar()).setTitle(R.string.authorization_configuration);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        titleEditText = findViewById(R.id.title_edit_text);
        subtitleEditText = findViewById(R.id.subtitle_key_edit_text);
        descriptionEditText = findViewById(R.id.description_key_edit_text);
        negativeButtonEditText = findViewById(R.id.negative_button_key_edit_text);
        deviceCredentialsSwitch = findViewById(R.id.device_credentials_switch);
        requireConfirmationSwitch = findViewById(R.id.confirmation_required_switch);
        FloatingActionButton updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> updateItem());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onResume() {
        super.onResume();
        SmartTask.with(this)
                .assign(() -> storageApi.getItemDetailsById(SmartCredentialsFilterFactory.createNonSensitiveItemFilter(ITEM_ID, ITEM_TYPE)))
                .finish(result -> {
                    SmartCredentialsApiResponse<ItemEnvelope> response =
                            (SmartCredentialsApiResponse<ItemEnvelope>) result;
                    if (response != null && response.isSuccessful()) {
                        try {
                            titleEditText.setText(response.getData().getDetails().getString(ITEM_KEY_TITLE));
                            descriptionEditText.setText(response.getData().getDetails().getString(ITEM_KEY_DESCRIPTION));
                            subtitleEditText.setText(response.getData().getDetails().getString(ITEM_KEY_SUBTITLE));
                            negativeButtonEditText.setText(response.getData().getDetails().getString(ITEM_KEY_NEGATIVE_BUTTON_TEXT));
                            deviceCredentialsSwitch.setChecked(response.getData().getDetails().getBoolean(ITEM_KEY_DEVICE_CREDENTIALS_FALLBACK));
                            requireConfirmationSwitch.setChecked(response.getData().getDetails().getBoolean(ITEM_KEY_REQUIRE_CONFIRMATION));
                        } catch (JSONException e) {
                            Timber.tag(DemoApplication.TAG).d("Failed to fetch identifier or details.");
                        }
                    } else {
                        Timber.tag(DemoApplication.TAG).d("Failed to fetch item details.");
                    }
                })
                .execute();
    }

    private void updateItem() {
        SmartTask.with(this)
                .assign(() -> {
                    String title = String.valueOf(titleEditText.getText());
                    String subtitle = String.valueOf(subtitleEditText.getText());
                    String description = String.valueOf(descriptionEditText.getText());
                    String negativeButtonText = String.valueOf(negativeButtonEditText.getText());
                    boolean deviceCredentialsFallback = deviceCredentialsSwitch.isChecked();
                    boolean requireConfirmation = requireConfirmationSwitch.isChecked();
                    JSONObject identifierJson = new JSONObject();
                    JSONObject detailsJson = new JSONObject();
                    try {
                        detailsJson.put(ITEM_KEY_TITLE, title);
                        detailsJson.put(ITEM_KEY_SUBTITLE, subtitle);
                        detailsJson.put(ITEM_KEY_DESCRIPTION, description);
                        detailsJson.put(ITEM_KEY_NEGATIVE_BUTTON_TEXT, negativeButtonText);
                        detailsJson.put(ITEM_KEY_DEVICE_CREDENTIALS_FALLBACK, deviceCredentialsFallback);
                        detailsJson.put(ITEM_KEY_REQUIRE_CONFIRMATION, requireConfirmation);

                        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(ITEM_ID, identifierJson, detailsJson);
                        return storageApi.putItem(itemEnvelope,
                                ItemContextFactory.createNonEncryptedNonSensitiveItemContext(ITEM_TYPE));
                    } catch (JSONException e) {
                        Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.");
                    }
                    return null;
                })
                .finish(result -> finish())
                .execute();
    }
}
