package de.telekom.scotpdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.telekom.scotpdemo.DemoApplication;
import de.telekom.scotpdemo.PreferenceManager;
import de.telekom.scotpdemo.R;
import de.telekom.scotpdemo.add.AddOtpActivity;
import de.telekom.scotpdemo.filter.OtpTypeActivity;
import de.telekom.scotpdemo.task.SmartTask;
import de.telekom.smartcredentials.core.api.OtpApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.otp.HOTPCallback;
import de.telekom.smartcredentials.core.otp.HOTPHandler;
import de.telekom.smartcredentials.core.otp.HOTPHandlerCallback;
import de.telekom.smartcredentials.core.otp.OTPHandlerFailed;
import de.telekom.smartcredentials.core.otp.OTPPluginError;
import de.telekom.smartcredentials.core.otp.TOTPCallback;
import de.telekom.smartcredentials.core.otp.TOTPHandler;
import de.telekom.smartcredentials.core.otp.TOTPHandlerCallback;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.security.MacAlgorithm;
import de.telekom.smartcredentials.otp.factory.SmartCredentialsOtpFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OtpAdapter.OtpClickListener {

    private OtpApi otpApi;
    private StorageApi storageApi;
    private OtpAdapter adapter;

    private RecyclerView recyclerView;
    private TextView statusText;
    private final Map<ItemEnvelope, HOTPHandler> HOTPHandlers = new HashMap<>();
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        statusText = findViewById(R.id.status_text_view);
        otpApi = SmartCredentialsOtpFactory.getOtpApi();
        storageApi = SmartCredentialsStorageFactory.getStorageApi();
        preferenceManager = new PreferenceManager(this);
        adapter = new OtpAdapter();
        adapter.setListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
        fetchData();
    }

    @SuppressWarnings("unchecked")
    private void fetchData() {
        recyclerView.setVisibility(View.GONE);
        statusText.setText(R.string.loading);
        statusText.setVisibility(View.VISIBLE);
        OTPType otpType = preferenceManager.getOtpType();
        SmartTask.with(this)
                .assign(() -> {
                    SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(otpType.getDesc());
                    return storageApi.getAllItemsByItemType(filter);
                }).finish(result -> {
            SmartCredentialsApiResponse<List<ItemEnvelope>> response =
                    (SmartCredentialsApiResponse<List<ItemEnvelope>>) result;
            if (response != null && response.isSuccessful()) {
                if (response.getData().size() > 0) {
                    List<ItemEnvelope> otpList = response.getData();
                    adapter.setData(otpType, otpList);
                    generateOTPValues(otpType, otpList);
                } else {
                    statusText.setText(getString(R.string.add_otp_hint, otpType.name()));
                    statusText.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(MainActivity.this,
                        getString(R.string.failed_to_retrieve_items, otpType.name()),
                        Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void generateOTPValues(OTPType otpType, List<ItemEnvelope> itemEnvelopeList) {
        if (otpType == OTPType.TOTP) {
            for (ItemEnvelope item : itemEnvelopeList) {
                generateTOTPValue(item);
            }
        } else {
            for (ItemEnvelope item : itemEnvelopeList) {
                generateHOTPValue(item);
            }
        }
    }

    private void setTitle() {
        setTitle(preferenceManager.getOtpType().name());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                startActivity(new Intent(this, OtpTypeActivity.class));
                return true;
            case R.id.add:
                startActivity(new Intent(this, AddOtpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("unchecked")
    protected void generateHOTPValue(ItemEnvelope itemEnvelope) {
        SmartTask.with(this)
                .assign(() -> otpApi.createHOTPGenerator(itemEnvelope.getItemId(), new HOTPHandlerCallback() {
                     @Override
                     public void onOTPHandlerReady(HOTPHandler hotpHandler) {
                         HOTPHandlers.put(itemEnvelope, hotpHandler);
                         hotpHandler.generateHOTP(new HOTPCallback() {
                             @Override
                             public void onOTPGenerated(String s) {
                                 onNewOTPGenerated(itemEnvelope, new TokenResponse(s, System.currentTimeMillis()));
                             }

                             @Override
                             public void onFailed(OTPPluginError otpPluginError) {
                                 new Handler(Looper.getMainLooper()).post(() ->
                                         Toast.makeText(MainActivity.this,
                                                 getString(R.string.otp_error, itemEnvelope.getItemId(), otpPluginError.getDesc()),
                                                 Toast.LENGTH_SHORT).show());
                             }
                         }, MacAlgorithm.SHA256);
                     }

                     @Override
                     public void onOTPHandlerInitializationFailed(OTPHandlerFailed otpHandlerFailed) {
                         Timber.tag(DemoApplication.TAG).d(otpHandlerFailed.getDesc());
                     }
                 })).finish(result -> {
            SmartCredentialsApiResponse<Void> response = (SmartCredentialsApiResponse<Void>) result;
            if (response!= null && !response.isSuccessful()) {
                Throwable error = response.getError();
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    protected void generateTOTPValue(ItemEnvelope itemEnvelope) {
        SmartCredentialsApiResponse<Void> response = otpApi.createTOTPGenerator(itemEnvelope.getItemId(), new TOTPHandlerCallback() {
            @Override
            public void onOTPHandlerReady(TOTPHandler totpHandler) {
                totpHandler.startGeneratingTOTP(new TOTPCallback() {
                    @Override
                    public void onOTPGenerated(TokenResponse tokenResponse) {
                        onNewOTPGenerated(itemEnvelope, tokenResponse);
                    }

                    @Override
                    public void onFailed(OTPPluginError otpPluginError) {
                        totpHandler.stop();
                        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(MainActivity.this,
                                getString(R.string.otp_error, itemEnvelope.getItemId(), otpPluginError.getDesc()),
                                Toast.LENGTH_SHORT).show()
                        );
                    }
                }, MacAlgorithm.SHA256);
            }

            @Override
            public void onOTPHandlerInitializationFailed(OTPHandlerFailed otpHandlerFailed) {
                Timber.tag(DemoApplication.TAG).d(otpHandlerFailed.getDesc());
            }
        });

        if (!response.isSuccessful()) {
            Throwable error = response.getError();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onNewOTPGenerated(ItemEnvelope itemEnvelope, TokenResponse tokenResponse) {
        adapter.updateItem(itemEnvelope, tokenResponse, () -> {
            if (recyclerView != null) {
                statusText.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onOtpAccountClick(ItemEnvelope itemEnvelope) {
        if (preferenceManager.getOtpType() == OTPType.HOTP) {
            Objects.requireNonNull(HOTPHandlers.get(itemEnvelope)).generateHOTP(new HOTPCallback() {
                @Override
                public void onOTPGenerated(String otpValue) {
                    onNewOTPGenerated(itemEnvelope, new TokenResponse(otpValue, System.currentTimeMillis()));
                }

                @Override
                public void onFailed(OTPPluginError otpPluginError) {
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(MainActivity.this, getString(R.string.otp_error,
                                    itemEnvelope.getItemId(), otpPluginError.getDesc()), Toast.LENGTH_SHORT)
                                    .show());
                }
            }, MacAlgorithm.SHA256);
        }
    }

    @Override
    public void onOptDeleteClick(ItemEnvelope itemEnvelope) {
        SmartTask.with(this).assign(() -> {
            SmartCredentialsFilter filter = SmartCredentialsFilterFactory
                    .createSensitiveItemFilter(itemEnvelope.getItemId(), preferenceManager.getOtpType().getDesc());
            return storageApi.deleteItem(filter);
        }).finish(result -> {
            adapter.removeItem(itemEnvelope);
            if (adapter.getData().isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                statusText.setVisibility(View.VISIBLE);
                statusText.setText(getString(R.string.add_otp_hint, preferenceManager.getOtpType().name()));
            }
        }).execute();
    }
}