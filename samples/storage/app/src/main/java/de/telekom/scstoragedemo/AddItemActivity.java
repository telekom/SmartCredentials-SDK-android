package de.telekom.scstoragedemo;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private static final String ITEM_ID = "item_id";
    public static final String ITEM_TYPE = "item_type";
    private static final String ITEM_KEY = "item_key";

    private StorageApi storageApi;
    private EditText idEditText;
    private EditText typeEditText;
    private EditText keyEditText;
    private EditText identifierEditText;
    private EditText detailsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        storageApi = SmartCredentialsStorageFactory.getStorageApi();

        idEditText = findViewById(R.id.id_edit_text);
        typeEditText = findViewById(R.id.type_edit_text);
        keyEditText = findViewById(R.id.key_edit_text);
        identifierEditText = findViewById(R.id.identifier_edit_text);
        detailsEditText = findViewById(R.id.details_edit_text);
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            addItem();
        });
    }

    private void addItem() {
        String itemId = String.valueOf(idEditText.getText());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ITEM_KEY, "test");
            ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(itemId, jsonObject);
            ItemContext itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(ITEM_TYPE);
            storageApi.putItem(itemEnvelope, itemContext);
            finish();
        } catch (JSONException e) {
            Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.");
        }
    }
}