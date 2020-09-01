package de.telekom.authenticationdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class MainActivity extends AppCompatActivity {

    private AuthenticationApi authenticationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout_item) {
            LogoutDialogFragment logoutDialogFragment = new LogoutDialogFragment();
            logoutDialogFragment.show(getSupportFragmentManager(), LogoutDialogFragment.TAG);
            new Thread(() -> {
                SmartCredentialsApiResponse<Boolean> response = authenticationApi.logOut();
                runOnUiThread(() -> {
                    logoutDialogFragment.dismiss();
                    if (response.isSuccessful() && response.getData()) {
                        startActivity(new Intent(this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, R.string.logout_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}