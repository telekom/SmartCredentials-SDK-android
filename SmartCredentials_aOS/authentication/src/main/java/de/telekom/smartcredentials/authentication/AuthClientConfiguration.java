/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import de.telekom.smartcredentials.authentication.di.ObjectGraphCreatorAuthentication;
import de.telekom.smartcredentials.authentication.exception.InvalidConfigurationException;
import de.telekom.smartcredentials.authentication.parser.JsonParser;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on February 27, 2019.
 */
public class AuthClientConfiguration {

    private static final String TAG = "AuthClientConfiguration";
    private static final String KEY_LAST_HASH = "config_hash";
    private static final String CONFIG_KEY_CLIENT_ID = "client_id";
    private static final String CONFIG_KEY_AUTH_SCOPE = "authorization_scope";
    private static final String CONFIG_KEY_REDIRECT_URI = "redirect_uri";
    private static final String CONFIG_KEY_DISCOVERY_URI = "discovery_uri";
    private static final String CONFIG_KEY_AUTH_ENDPOINT_URI = "authorization_endpoint_uri";
    private static final String CONFIG_KEY_TOKEN_ENDPOINT_URI = "token_endpoint_uri";
    private static final String CONFIG_KEY_USER_INFO_ENDPOINT_URI = "user_info_endpoint_uri";
    private static final String CONFIG_KEY_REGISTRATION_ENDPOINT_URI = "registration_endpoint_uri";
    private static final String CONFIG_KEY_HTTPS_REQUIRED = "https_required";
    private static final String REDIRECT_URI_NOT_HANDLED_ERROR = "redirect_uri is not handled " +
            "by any activity in this app! " +
            "Ensure that the appAuthRedirectScheme in your build.gradle file is correctly configured, " +
            "or that an appropriate intent filter exists in your app manifest!";

    private final PackageManager mPackageManager;
    private final String mPackageName;
    private final String mIdentityProviderId;
    private String mConfigError;
    private String mClientId;
    private String mScope;
    private Uri mRedirectUri;
    private Uri mDiscoveryUri;
    private Uri mAuthEndpointUri;
    private Uri mTokenEndpointUri;
    private Uri mUserInfoEndpointUri;
    private Uri mRegistrationEndpointUri;
    private boolean mHttpsRequired;
    private int mConfigHash;
    private AuthenticationStorageRepository mAuthenticationStorageRepository;

    private AuthClientConfiguration(Context context, int authConfigResId,
                                    String providerId) {
        mPackageName = context.getPackageName();
        mPackageManager = context.getPackageManager();
        mIdentityProviderId = providerId;
        mAuthenticationStorageRepository = ObjectGraphCreatorAuthentication.getInstance().provideAuthenticationStorageRepository();

        InputStream configInputStream = context.getResources().openRawResource(authConfigResId);
        try {
            readConfiguration(configInputStream);
        } catch (InvalidConfigurationException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
            mConfigError = e.getMessage();
        }
    }

    /**
     * Entry point for creating an instance of {@link AuthClientConfiguration}
     *
     * @param context         Application context
     * @param authConfigResId Resource ID of configuration file stored in assets' raw folder
     * @return The instance of {@link AuthClientConfiguration} if already created or creates a new one
     */
    public static AuthClientConfiguration getInstance(Context context, int authConfigResId, String providerId) {
        return new AuthClientConfiguration(context.getApplicationContext(), authConfigResId, providerId);
    }

    public boolean hasConfigurationChanges() {
        Integer lastConfig = getLastKnownConfigHash();
        return lastConfig == null || mConfigHash != lastConfig;
    }

    public boolean isValid() {
        return mConfigError == null;
    }

    public String getConfigError() {
        return mConfigError;
    }

    public void acceptConfiguration() {
        mAuthenticationStorageRepository.saveAuthConfigValue(computeHashKey(), String.valueOf(mConfigHash));
    }

    public String getClientId() {
        return mClientId;
    }

    public Uri getRedirectUri() {
        return mRedirectUri;
    }

    public String getScope() {
        return mScope;
    }

    @SuppressWarnings("unused")
    boolean isHttpsRequired() {
        return mHttpsRequired;
    }

    public Uri getDiscoveryUri() {
        return mDiscoveryUri;
    }

    public Uri getAuthEndpointUri() {
        return mAuthEndpointUri;
    }

    public Uri getTokenEndpointUri() {
        return mTokenEndpointUri;
    }

    public Uri getRegistrationEndpointUri() {
        return mRegistrationEndpointUri;
    }

    public Uri getUserInfoEndpointUri() {
        return mUserInfoEndpointUri;
    }

    private void readConfiguration(InputStream configInputStream) throws InvalidConfigurationException {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(configInputStream, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException ex) {
            throw new InvalidConfigurationException("Failed to read config file: " + ex.getMessage());
        } finally {
            try {
                configInputStream.close();
            } catch (IOException ignored) {
            }
        }

        String jsonStringConfig = writer.toString();

        try {
            parseJSONConfig(new JSONObject(jsonStringConfig));
        } catch (JSONException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
    }

    private void parseJSONConfig(JSONObject jsonConfig) throws InvalidConfigurationException {
        mConfigHash = jsonConfig.toString().hashCode();

        JsonParser parser = JsonParser.forJson(jsonConfig);

        mClientId = parser.getString(CONFIG_KEY_CLIENT_ID);
        mScope = parser.getRequiredString(CONFIG_KEY_AUTH_SCOPE);
        mRedirectUri = parser.getRequiredUri(CONFIG_KEY_REDIRECT_URI);

        if (!isRedirectUriRegistered()) {
            throw new InvalidConfigurationException(REDIRECT_URI_NOT_HANDLED_ERROR);
        }

        if (parser.getString(CONFIG_KEY_DISCOVERY_URI) == null) {
            mAuthEndpointUri = parser.getRequiredWebUri(CONFIG_KEY_AUTH_ENDPOINT_URI);
            mTokenEndpointUri = parser.getRequiredWebUri(CONFIG_KEY_TOKEN_ENDPOINT_URI);
            mUserInfoEndpointUri = parser.getRequiredWebUri(CONFIG_KEY_USER_INFO_ENDPOINT_URI);

            if (mClientId == null) {
                mRegistrationEndpointUri = parser.getRequiredWebUri(CONFIG_KEY_REGISTRATION_ENDPOINT_URI);
            }
        } else {
            mDiscoveryUri = parser.getRequiredWebUri(CONFIG_KEY_DISCOVERY_URI);
        }

        mHttpsRequired = jsonConfig.optBoolean(CONFIG_KEY_HTTPS_REQUIRED, true);
    }

    private boolean isRedirectUriRegistered() {
        Intent redirectIntent = new Intent();
        redirectIntent.setPackage(mPackageName);
        redirectIntent.setAction(Intent.ACTION_VIEW);
        redirectIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        redirectIntent.setData(mRedirectUri);

        return !mPackageManager.queryIntentActivities(redirectIntent, 0).isEmpty();
    }

    private Integer getLastKnownConfigHash() {
        String hash = mAuthenticationStorageRepository.getAuthConfig(computeHashKey());
        return hash == null ? null : Integer.valueOf(hash);
    }

    private String computeHashKey() {
        return KEY_LAST_HASH + "_" + mIdentityProviderId;
    }
}
