package de.telekom.scstoragedemo;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

public class AddItemActivity extends AppCompatActivity {

    public static final String ITEM_TYPE = "test_type";
    public static final String ITEM_KEY_IDENTIFIER = "test_identifier_key";
    public static final String ITEM_KEY_DETAILS = "test_details_key";

    private StorageApi storageApi;
    private EditText idEditText;
    private EditText identifierEditText;
    private EditText detailsEditText;
    private SwitchMaterial sensitiveSwitch;
    private SwitchMaterial encryptedSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        idEditText = findViewById(R.id.id_edit_text);
        identifierEditText = findViewById(R.id.identifier_key_edit_text);
        detailsEditText = findViewById(R.id.details_key_edit_text);
        sensitiveSwitch = findViewById(R.id.sensitive_switch);
        encryptedSwitch = findViewById(R.id.encrypted_switch);
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> addItem());
    }

    private void addItem() {
        String itemId = String.valueOf(idEditText.getText());
        String identifier = String.valueOf(identifierEditText.getText());
        String details = String.valueOf(detailsEditText.getText());
        JSONObject identifierJson = new JSONObject();
        JSONObject detailsJson = new JSONObject();
        try {
            identifierJson.put(ITEM_KEY_IDENTIFIER, identifier);
            detailsJson.put(ITEM_KEY_DETAILS, details);
            new Thread(() -> {
                ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, identifierJson, detailsJson);
                storageApi.putItem(itemEnvelope, getItemContext());
                runOnUiThread(this::finish);
            }).start();
        } catch (JSONException e) {
            Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.");
        }
    }

    private ItemContext getItemContext() {
        if (sensitiveSwitch.isChecked()) {
            if (encryptedSwitch.isChecked()) {
                return ItemContextFactory.createEncryptedSensitiveItemContext(ITEM_TYPE);
            } else {
                return ItemContextFactory.createNonEncryptedSensitiveItemContext(ITEM_TYPE);
            }
        } else {
            if (encryptedSwitch.isChecked()) {
                return ItemContextFactory.createEncryptedNonSensitiveItemContext(ITEM_TYPE);
            } else {
                return ItemContextFactory.createNonEncryptedNonSensitiveItemContext(ITEM_TYPE);
            }
        }
    }
}