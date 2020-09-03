package de.telekom.scauthorizationdemo;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "authorization_tag";

    private TextView authorizationStatus;
    private ImageView authorizationIcon;
    private AuthorizationApi api;
    private Fragment authorizationDialog;
    private BiometricAuthorizationPresenter biometricsAuthorizationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = SmartCredentialsAuthorizationFactory.getAuthorizationApi();
        authorizationStatus = findViewById(R.id.authorize_status);
        authorizationIcon = findViewById(R.id.authorization_icon);
        Button authorizeButton = findViewById(R.id.authorize_button);
        authorizeButton.setOnClickListener(v -> authorizeUser());
    }

    private void authorizeUser() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            requestAuthorizationPreApi28();
        } else {
            requestAuthorizationPostApi28();
        }
    }

    private void requestAuthorizationPreApi28() {
        SmartCredentialsApiResponse<Fragment> response = api.getAuthorizeUserFragment(getAuthorizationCallback());

        if (response.isSuccessful()) {
            authorizationDialog = response.getData();
            if (authorizationDialog == null) {
                setStatus(getString(R.string.error_device_not_secured), R.drawable.ic_error);
            } else if (authorizationDialog instanceof DialogFragment) {
                ((DialogFragment) authorizationDialog).show(this.getSupportFragmentManager(),
                        DialogFragment.class.getSimpleName());
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(authorizationDialog, TAG)
                        .addToBackStack(null)
                        .commit();
            }
        } else {
            Toast.makeText(this, R.string.error_performing_authorization, Toast.LENGTH_SHORT).show();
        }
    }

    private void requestAuthorizationPostApi28() {
        SmartCredentialsApiResponse<BiometricAuthorizationPresenter> response =
                api.getBiometricAuthorizationPresenter(getAuthorizationCallback());
        if (response.isSuccessful()) {
            biometricsAuthorizationPresenter = response.getData();
            if (biometricsAuthorizationPresenter == null) {
                Toast.makeText(this, R.string.error_device_not_secured, Toast.LENGTH_SHORT).show();
            }
            biometricsAuthorizationPresenter.viewReady(new BiometricView(
                    getString(R.string.biometrics_view_title),
                    getString(R.string.biometrics_view_subtitle),
                    getString(R.string.biometrics_view_description),
                    getString(R.string.biometrics_view_negative_button_description),
                    (dialog, which) -> {
                        biometricsAuthorizationPresenter.stopListeningForUserAuth();
                        setStatus(AuthorizationPluginError.AUTH_CANCELED.getDesc(), R.drawable.ic_error);
                    }));
            biometricsAuthorizationPresenter.startListeningForUserAuth();
        } else {
            Toast.makeText(this, R.string.error_performing_authorization, Toast.LENGTH_SHORT).show();
        }
    }

    private AuthorizationCallback getAuthorizationCallback() {
        return new AuthorizationCallback() {
            @Override
            public void onAuthorized() {
                new Handler().postDelayed(() -> {
                    if (authorizationDialog instanceof DialogFragment) {
                        ((DialogFragment) authorizationDialog).dismiss();
                    } else if (biometricsAuthorizationPresenter != null) {
                        biometricsAuthorizationPresenter.stopListeningForUserAuth();
                    } else {
                        getSupportFragmentManager().popBackStackImmediate();
                    }
                    setStatus(getString(R.string.successful_authorization), R.drawable.ic_success);
                }, 300);
            }

            @Override
            public void onUnavailable(AuthorizationPluginUnavailable error) {
                setStatus(error.getDesc(), R.drawable.ic_error);
            }

            @Override
            public void onFailure(AuthorizationPluginError error) {
                if (authorizationDialog != null) {
                    if (authorizationDialog instanceof DialogFragment) {
                        ((DialogFragment) authorizationDialog).dismiss();
                    } else if (biometricsAuthorizationPresenter != null) {
                        biometricsAuthorizationPresenter.stopListeningForUserAuth();
                    }
                }
                setStatus(error.getDesc(), R.drawable.ic_error);
            }
        };
    }

    private void setStatus(String status, int statusIcon) {
        authorizationStatus.setText(status);
        authorizationIcon.setImageDrawable(ContextCompat.getDrawable(this, statusIcon));
    }
}