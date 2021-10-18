package de.telekom.authenticationdemo.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import de.telekom.authenticationdemo.IdentityProvider;
import de.telekom.authenticationdemo.PkceProvider;
import de.telekom.authenticationdemo.R;
import de.telekom.authenticationdemo.task.SmartTask;
import de.telekom.authenticationdemo.tokens.TokensActivity;
import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;
import de.telekom.smartcredentials.core.authentication.configuration.AuthenticationConfiguration;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

public class LoginActivity extends AppCompatActivity implements AuthenticationServiceInitListener {

    private AuthenticationApi authenticationApi;
    private PkceProvider pkceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pkceProvider = new PkceProvider();

        authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();
        initializeApi();
        if (authenticationApi.isUserLoggedIn().isSuccessful() &&
                authenticationApi.isUserLoggedIn().getData()) {
            startActivity(new Intent(this, TokensActivity.class));
            finish();
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view ->
                SmartTask.with(LoginActivity.this)
                        .assign(() -> authenticationApi.isApiInitialized())
                        .finish(request -> {
                            SmartCredentialsResponse<Boolean> initState = (SmartCredentialsResponse<Boolean>) request;
                            if (initState != null && initState.getData() != null
                                    && initState.isSuccessful() && initState.getData()) {
                                login();
                            } else {
                                SmartTask.with(LoginActivity.this)
                                        .assign(this::initializeApi)
                                        .finish(result -> login())
                                        .execute();
                            }
                        })
                        .execute());
    }

    private void login() {
        SmartTask.with(LoginActivity.this)
                .assign(() -> {
                    Intent completionIntent = new Intent(this, CompletionActivity.class);
                    Intent cancelIntent = new Intent(this, CancelActivity.class);
                    return authenticationApi.login(this, completionIntent, cancelIntent);
                })
                .finish(result -> {
                    SmartCredentialsResponse<Boolean> response = (SmartCredentialsResponse<Boolean>) result;
                    if (response != null && response.isSuccessful()) {
                        Boolean loginState = response.getData();
                        if (loginState != null && loginState) {
                            finish();
                        }
                    }
                })
                .execute();
    }

    private SmartCredentialsApiResponse<Boolean> initializeApi() {
        return authenticationApi.initialize(new AuthenticationConfiguration.ConfigurationBuilder(
                this,
                IdentityProvider.GOOGLE.getName(),
                IdentityProvider.GOOGLE.getConfigId(),
                ContextCompat.getColor(this, R.color.colorPrimary))
                .setPkceConfiguration(pkceProvider.generatePkceConfiguration())
                .build(), this);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed(AuthenticationError errorDescription) {
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show();
    }
}