package de.telekom.scotpdemo.add;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.telekom.scotpdemo.BaseUpActivity;
import de.telekom.scotpdemo.DemoApplication;
import de.telekom.scotpdemo.R;
import de.telekom.scotpdemo.task.SmartTask;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 9/1/2020
 */
public class AddManualOtpActivity extends BaseUpActivity {

    public static final String ITEM_ACCOUNT_NAME = "user_label";
    public static final String ITEM_ACCOUNT_KEY = "key";

    private EditText accountName;
    private EditText accountKey;
    private Spinner otpTypeSpinner;

    private StorageApi storageApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manual_layout);
        storageApi = SmartCredentialsStorageFactory.getStorageApi();
        accountName = findViewById(R.id.account_name_edit_text);
        accountKey = findViewById(R.id.key_edit_text);
        setupSpinner();
        FloatingActionButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addItem());
    }

    private void setupSpinner() {
        otpTypeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<OTPType> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Arrays.asList(OTPType.values()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otpTypeSpinner.setAdapter(adapter);
    }

    private void addItem() {
        String name = accountName.getText().toString();
        String key = accountKey.getText().toString();

        if (name.isEmpty() || key.isEmpty()){
            finish();
            return;
        }
        OTPType type = (OTPType) otpTypeSpinner.getSelectedItem();

        JSONObject identifierJson = new JSONObject();
        JSONObject detailsJson = new JSONObject();
        try {
            identifierJson.put(ITEM_ACCOUNT_NAME, name);
            detailsJson.put(ITEM_ACCOUNT_KEY, key);
            SmartTask.with(this)
                    .assign(() -> {
                        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(
                                String.valueOf(System.currentTimeMillis()), identifierJson, detailsJson);
                        ItemContext itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(type.getDesc());
                        return storageApi.putItem(itemEnvelope, itemContext);
                    })
                    .finish(result -> finish())
                    .execute();
        } catch (JSONException e) {
            Timber.tag(DemoApplication.TAG).d("Failed to create item envelope.");
        }
    }
}
