package de.telekom.authenticationdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import de.telekom.smartcredentials.authentication.AuthStateManager;
import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView nameTextView;
    private TextView givenNameTextView;
    private TextView familyNameTextView;
    private TextView emailTextView;
    private ImageView emailImageView;
    private TextView localeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        imageView = findViewById(R.id.profile_image_view);
        nameTextView = findViewById(R.id.name_text_view);
        givenNameTextView = findViewById(R.id.given_name_text_view);
        familyNameTextView = findViewById(R.id.family_name_text_view);
        emailTextView = findViewById(R.id.email_value_text_view);
        emailImageView = findViewById(R.id.email_verified_image_view);
        localeTextView = findViewById(R.id.locale_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProfile();
    }

    private void fetchProfile() {
        new Thread(() -> {
            AuthenticationApi authenticationApi = SmartCredentialsAuthenticationFactory.getAuthenticationApi();
            SmartCredentialsApiResponse<String> response = authenticationApi.getUserInfoEndpointUri();

            if (response != null && response.isSuccessful()) {
                AuthStateManager authStateManager = AuthStateManager.getInstance(this, IdentityProvider.GOOGLE.getName());
                String accessToken = authStateManager.getAccessToken();
                try {
                    URL userInfoEndpoint = new URL(authenticationApi.getUserInfoEndpointUri().getData());
                    HttpURLConnection urlConnection = (HttpURLConnection) userInfoEndpoint.openConnection();
                    urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
                    urlConnection.setInstanceFollowRedirects(false);
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

                        StringBuilder sb = new StringBuilder();
                        String line;

                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        Profile profile = new Gson().fromJson(sb.toString(), Profile.class);
                        runOnUiThread(() -> {
                            Glide.with(this).load(profile.getPicture()).into(imageView);
                            nameTextView.setText(profile.getName());
                            givenNameTextView.setText(profile.getGivenName());
                            familyNameTextView.setText(profile.getFamilyName());
                            emailTextView.setText(profile.getEmail());
                            emailImageView.setVisibility(profile.isEmailVerified() ? View.VISIBLE : View.GONE);
                            localeTextView.setText(profile.getLocale());
                        });
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, R.string.user_info_uri_failed, Toast.LENGTH_SHORT).show();
            }
        }).start();
    }
}