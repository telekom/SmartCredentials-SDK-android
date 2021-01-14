package de.telekom.scstoragedemo.edit;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.scstoragedemo.DemoApplication;
import de.telekom.scstoragedemo.R;
import de.telekom.scstoragedemo.add.AddItemActivity;
import de.telekom.scstoragedemo.task.SmartTask;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by Alex.Graur@endava.com at 8/27/2020
 */
public class ItemDetailsActivity extends AppCompatActivity {

    public final static String EXTRA_ITEM_ID = "extra_item_id";
    public final static String EXTRA_ITEM_SENSITIVE = "extra_item_sensitive";

    private StorageApi storageApi;
    private EditText idEditText;
    private EditText identifierEditText;
    private EditText detailsEditText;
    private SwitchMaterial encryptedSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        idEditText = findViewById(R.id.id_edit_text);
        identifierEditText = findViewById(R.id.identifier_key_edit_text);
        detailsEditText = findViewById(R.id.details_key_edit_text);
        encryptedSwitch = findViewById(R.id.encrypted_switch);
        FloatingActionButton updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> updateItem());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onResume() {
        super.onResume();
        SmartTask.with(this)
                .assign(() -> storageApi.getItemDetailsById(getFetchItemFilter()))
                .finish(result -> {
                    SmartCredentialsApiResponse<ItemEnvelope> response =
                            (SmartCredentialsApiResponse<ItemEnvelope>) result;
                    if (response != null && response.isSuccessful()) {
                        idEditText.setText(response.getData().getItemId());
                        try {
                            identifierEditText.setText(response.getData().getIdentifier().getString(AddItemActivity.ITEM_KEY_IDENTIFIER));
                            detailsEditText.setText(response.getData().getDetails().getString(AddItemActivity.ITEM_KEY_DETAILS));
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
                    String itemId = String.valueOf(idEditText.getText());
                    String identifier = String.valueOf(identifierEditText.getText());
                    String details = String.valueOf(detailsEditText.getText());
                    JSONObject identifierJson = new JSONObject();
                    JSONObject detailsJson = new JSONObject();
                    try {
                        identifierJson.put(AddItemActivity.ITEM_KEY_IDENTIFIER, identifier);
                        detailsJson.put(AddItemActivity.ITEM_KEY_DETAILS, details);

                        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, identifierJson, detailsJson);
                        return storageApi.updateItem(itemEnvelope, getItemContext());
                    } catch (JSONException e) {
                        Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.");
                    }
                    return null;
                })
                .finish(result -> finish())
                .execute();
    }

    public SmartCredentialsFilter getFetchItemFilter() {
        String itemId = getIntent().getStringExtra(EXTRA_ITEM_ID);
        if (getIntent().getBooleanExtra(EXTRA_ITEM_SENSITIVE, true)) {
            return SmartCredentialsFilterFactory.createSensitiveItemFilter(itemId, AddItemActivity.ITEM_TYPE);
        } else {
            return SmartCredentialsFilterFactory.createNonSensitiveItemFilter(itemId, AddItemActivity.ITEM_TYPE);
        }
    }

    private ItemContext getItemContext() {
        if (getIntent().getBooleanExtra(EXTRA_ITEM_SENSITIVE, true)) {
            if (encryptedSwitch.isChecked()) {
                return ItemContextFactory.createEncryptedSensitiveItemContext(AddItemActivity.ITEM_TYPE);
            } else {
                return ItemContextFactory.createNonEncryptedSensitiveItemContext(AddItemActivity.ITEM_TYPE);
            }
        } else {
            if (encryptedSwitch.isChecked()) {
                return ItemContextFactory.createEncryptedNonSensitiveItemContext(AddItemActivity.ITEM_TYPE);
            } else {
                return ItemContextFactory.createNonEncryptedNonSensitiveItemContext(AddItemActivity.ITEM_TYPE);
            }
        }
    }
}
