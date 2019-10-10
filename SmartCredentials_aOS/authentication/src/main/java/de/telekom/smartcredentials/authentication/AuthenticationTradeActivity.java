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

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import de.telekom.smartcredentials.authentication.parser.BundleTransformer;
import de.telekom.smartcredentials.core.logger.ApiLogger;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_AUTH_CONFIG_FILE_ID;
import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_CANCEL_INTENT_CLASS;
import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_CANCEL_INTENT_EXTRAS;
import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_COMPLETE_INTENT_CLASS;
import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_COMPLETE_INTENT_EXTRAS;
import static de.telekom.smartcredentials.authentication.SmartCredentialsAuthenticationService.KEY_IDENTITY_PROVIDER_ID;

/**
 * Activity responsible for trading authorization code for access, refresh, other tokens.
 * This activity will not be shown to the end user as it only performs a request and
 * redirects the flow to a completion intent in case of success or to a cancel intent in other cases.
 * <p>
 * After sending any of the intents this activity calls {@link Activity#finish()}.
 * For error codes and responses please check your {@link ApiLogger} implementation
 * in which the logs will be forwarded.
 * <p>
 * Created by Lucian Iacob on March 01, 2019.
 */
public class AuthenticationTradeActivity extends Activity {

    private static final String TAG = "AuthenticationTradeActivity";

    private AuthorizationService mAuthService;
    private Intent mCancelIntent;
    private Intent mCompleteIntent;
    private int mConfigFileId;
    private String mProviderId;
    private AuthStateManager mStateManager;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthService = new AuthorizationService(this, AppAuthConfiguration.DEFAULT);

        extractPrefs();
        validateInputs();

        mStateManager = AuthStateManager.getInstance(this, mProviderId);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AuthClientConfiguration config = AuthClientConfiguration.getInstance(this,
                mConfigFileId,
                mProviderId);

        if (config.hasConfigurationChanges()) {
            logOut();
            return;
        }

        AuthorizationResponse response = AuthorizationResponse.fromIntent(getIntent());
        AuthorizationException exception = AuthorizationException.fromIntent(getIntent());

        if (response != null || exception != null) {
            mStateManager.updateAfterAuthorization(response, exception);
        }

        if (response != null && response.authorizationCode != null) {
            tradeAuthorizationCodeForTokens(response, exception);
        } else if (exception != null) {
            sendIntent(mCancelIntent);
        } else {
            sendIntent(mCancelIntent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthService.dispose();
    }

    /**
     * Activity entry point
     *
     * @param applicationContext Application context
     * @return {@link PendingIntent} for starting the {@link AuthenticationTradeActivity)}
     */
    static PendingIntent createStartIntent(Context applicationContext) {
        Intent intent = new Intent(applicationContext, AuthenticationTradeActivity.class);
        return PendingIntent.getActivity(applicationContext, 0, intent, 0);
    }

    private void extractPrefs() {
        SharedPreferences prefs = getSharedPreferences(AuthClientConfiguration.CONFIG_PREFS_NAME, MODE_PRIVATE);

        mProviderId = prefs.getString(KEY_IDENTITY_PROVIDER_ID, null);
        mConfigFileId = prefs.getInt(KEY_AUTH_CONFIG_FILE_ID, -1);
        final String completeIntentClass = prefs.getString(KEY_COMPLETE_INTENT_CLASS, null);
        final String cancelIntentClass = prefs.getString(KEY_CANCEL_INTENT_CLASS, null);
        final String completeIntentEncodedExtras = prefs.getString(KEY_COMPLETE_INTENT_EXTRAS, null);
        final String cancelIntentEncodedExtras = prefs.getString(KEY_CANCEL_INTENT_EXTRAS, null);

        if (completeIntentClass != null && cancelIntentClass != null) {
            try {
                final Class<?> completeClass = Class.forName(completeIntentClass);
                final Class<?> cancelClass = Class.forName(cancelIntentClass);

                mCompleteIntent = new Intent(this, completeClass);
                mCancelIntent = new Intent(this, cancelClass);

                final Bundle completeExtras = BundleTransformer.decodeFromString(completeIntentEncodedExtras);
                if (completeExtras != null) {
                    mCompleteIntent.putExtras(completeExtras);
                }
                final Bundle cancelExtras = BundleTransformer.decodeFromString(cancelIntentEncodedExtras);
                if (cancelExtras != null) {
                    mCancelIntent.putExtras(cancelExtras);
                }
            } catch (ClassNotFoundException e) {
                ApiLoggerResolver.logError(TAG, "Error extracting completion and cancel classes.");
            }
        }
    }

    @WorkerThread
    private void handleTokenResponse(TokenResponse tokenResponse, AuthorizationException exception) {
        mStateManager.updateAfterTokenResponse(tokenResponse, exception);

        if (!mStateManager.getCurrent().isAuthorized()) {
            String message = "Authorization Code exchange failed" + ((exception != null) ? exception.error : "");
            ApiLoggerResolver.logError(TAG, message);
            runOnUiThread(() -> sendIntent(mCancelIntent));
        } else {
            runOnUiThread(() -> sendIntent(mCompleteIntent));
        }
    }

    private void logOut() {
        // discarding the authorization and token state

        AuthState currentState = mStateManager.getCurrent();
        AuthState clearedState = new AuthState(currentState.getAuthorizationServiceConfiguration());
        if (currentState.getLastRegistrationResponse() != null) {
            clearedState.update(currentState.getLastRegistrationResponse());
        }
        mStateManager.replace(clearedState);

        sendIntent(mCancelIntent);
    }

    private void sendIntent(Intent intent) {
        startActivity(intent);
        finish();
    }

    private void tradeAuthorizationCodeForTokens(AuthorizationResponse response, AuthorizationException exception) {
        if (mStateManager.getCurrent().isAuthorized()) {
            sendIntent(mCompleteIntent);
            return;
        }

        try {
            ClientAuthentication clientAuthentication = mStateManager.getCurrent().getClientAuthentication();
            TokenRequest tokenRequest = response.createTokenExchangeRequest();
            mAuthService.performTokenRequest(tokenRequest, clientAuthentication, this::handleTokenResponse);
        } catch (ClientAuthentication.UnsupportedAuthenticationMethod unsupportedAuthenticationMethod) {
            ApiLoggerResolver.logError(TAG,
                    "Token request cannot be made, " +
                            "client authentication for the token endpoint " +
                            "could not be constructed (%s)" + exception.getMessage());
            sendIntent(mCancelIntent);
        }
    }

    private void validateInputs() {
        if (TextUtils.isEmpty(mProviderId)) {
            ApiLoggerResolver.logError(TAG, "Error: provider ID is empty.");
        }
        if (mConfigFileId == -1) {
            ApiLoggerResolver.logError(TAG, "Error: configuration file retrieval failed.");
        }
        if (mCompleteIntent == null) {
            ApiLoggerResolver.logError(TAG, "Error: Auth Completion intent is null.");
        }
        if (mCancelIntent == null) {
            ApiLoggerResolver.logError(TAG, "Error: Auth Cancellation intent is null.");
        }
    }
}
