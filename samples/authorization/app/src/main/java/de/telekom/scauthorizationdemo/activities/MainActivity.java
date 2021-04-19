package de.telekom.scauthorizationdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import de.telekom.scauthorizationdemo.mappers.ConfigurationMapper;
import de.telekom.scauthorizationdemo.R;
import de.telekom.scauthorizationdemo.callbacks.RetrieveConfigurationCallback;
import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;

public class MainActivity extends AppCompatActivity implements RetrieveConfigurationCallback {

    private TextView authorizationStatus;
    private ImageView authorizationIcon;
    private AuthorizationApi api;
    private ConfigurationMapper configurationMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
        configurationMapper = new ConfigurationMapper(this);
        authorizationStatus = findViewById(R.id.authorize_status);
        authorizationIcon = findViewById(R.id.authorization_icon);
        Button authorizeButton = findViewById(R.id.authorize_button);
        authorizeButton.setOnClickListener(v -> configurationMapper.retrieveAuthorizationConfiguration(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.configuration_item) {
            startActivity(new Intent(this, ConfigurationActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private AuthorizationCallback getAuthorizationCallback() {
        return new AuthorizationCallback() {
            @Override
            public void onAuthorizationSucceeded() {
                setStatus(getString(R.string.successful_authorization), R.drawable.ic_success);
            }

            @Override
            public void onAuthorizationError(String error) {
                setStatus(error, R.drawable.ic_error);
            }

            @Override
            public void onAuthorizationFailed(String error) {
                setStatus(error, R.drawable.ic_error);
            }
        };
    }

    private void setStatus(String status, int statusIcon) {
        authorizationStatus.setText(status);
        authorizationIcon.setImageDrawable(ContextCompat.getDrawable(this, statusIcon));
    }

    @Override
    public void onConfigurationRetrieved(AuthorizationConfiguration configuration) {
        api.authorize(this,
                configuration,
                getAuthorizationCallback());
    }

    @Override
    public void onFailedToRetrieveConfiguration() {
        setStatus("Please provide a valid Authorization Configuration", R.drawable.ic_error);
    }
}