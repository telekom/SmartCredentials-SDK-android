package de.telekom.authenticationdemo.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import de.telekom.authenticationdemo.IdentityProvider;
import de.telekom.authenticationdemo.R;
import de.telekom.authenticationdemo.task.SmartTask;
import de.telekom.authenticationdemo.tokens.TokensActivity;
import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;

public class LoginActivity extends AppCompatActivity implements AuthenticationServiceInitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AuthenticationApi authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();
        authenticationApi.initialize(this, IdentityProvider.GOOGLE.getName(),
                IdentityProvider.GOOGLE.getConfigId(), ContextCompat.getColor(this, R.color.colorPrimary),
                this);

        if (authenticationApi.isUserLoggedIn().isSuccessful() && authenticationApi.isUserLoggedIn().getData()) {
            startActivity(new Intent(this, TokensActivity.class));
            finish();
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(view ->
                SmartTask.with(LoginActivity.this)
                        .assign(() -> {
                            Intent completionIntent = new Intent(this, CompletionActivity.class);
                            Intent cancelIntent = new Intent(this, CancelActivity.class);
                            return authenticationApi.login(this, completionIntent, cancelIntent);
                        })
                        .finish(result -> finish())
                        .execute());
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