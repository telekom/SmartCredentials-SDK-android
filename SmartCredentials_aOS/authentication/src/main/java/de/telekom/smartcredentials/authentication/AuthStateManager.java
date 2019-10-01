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
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.RegistrationResponse;
import net.openid.appauth.TokenResponse;

import org.json.JSONException;

import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on February 27, 2019.
 */
public class AuthStateManager {

    private static final String AUTH_STATE_PREFS_NAME = "SmartCredentialsAuthState";
    private static final String KEY_AUTH_STATE = "auth_state";
    private static final String ERR_NULL_OR_EMPTY_PROVIDER = "Cannot use a null or empty identity provider ID.";

    private final SharedPreferences mSharedPreferences;
    private final ReentrantLock mPrefsLock;
    private final String mProviderId;

    private AuthStateManager(SharedPreferences sharedPreferences, String identityProviderId) {
        mSharedPreferences = sharedPreferences;
        mProviderId = identityProviderId;
        mPrefsLock = new ReentrantLock();
    }

    public static AuthStateManager getInstance(@NonNull Context context, @NonNull String providerId) {
        Objects.requireNonNull(context);
        if (TextUtils.isEmpty(providerId.trim())) {
            throw new IllegalArgumentException(ERR_NULL_OR_EMPTY_PROVIDER);
        }
        SharedPreferences prefs = context.getSharedPreferences(AUTH_STATE_PREFS_NAME, Context.MODE_PRIVATE);

        return new AuthStateManager(prefs, providerId);
    }

    @SuppressWarnings("unused")
    public boolean isAuthorized() {
        return getCurrent().isAuthorized();
    }

    public String getRefreshToken() {
        return getCurrent().getRefreshToken();
    }

    @SuppressWarnings("unused")
    public String getIdToken() {
        return getCurrent().getIdToken();
    }

    @SuppressWarnings("unused")
    public String getAccessToken() {
        return getCurrent().getAccessToken();
    }

    @SuppressWarnings("unused")
    public Long getAccessTokenExpirationTime() {
        return getCurrent().getAccessTokenExpirationTime();
    }

    AuthState getCurrent() {
        return readState();
    }

    void updateAfterTokenResponse(TokenResponse tokenResponse, AuthorizationException exception) {
        AuthState authState = getCurrent();
        authState.update(tokenResponse, exception);
        replace(authState);
    }

    void updateAfterAuthorization(AuthorizationResponse response, AuthorizationException exception) {
        AuthState state = getCurrent();
        state.update(response, exception);
        replace(state);
    }

    void replace(AuthState authState) {
        writeState(authState);
    }

    void updateAfterRegistration(RegistrationResponse registrationResponse, AuthorizationException ex) {
        AuthState authState = getCurrent();
        if (ex != null) {
            return;
        }

        authState.update(registrationResponse);
        replace(authState);
    }

    private AuthState readState() {
        mPrefsLock.lock();

        try {
            String currentState = mSharedPreferences.getString(computeAuthStateKey(), null);
            if (currentState == null) {
                return new AuthState();
            }

            try {
                return AuthState.jsonDeserialize(currentState);
            } catch (JSONException e) {
                ApiLoggerResolver.logError("AuthStateManager", "Failed to deserialize auth state. Will discart");
                return new AuthState();
            }
        } finally {
            mPrefsLock.unlock();
        }
    }

    private void writeState(AuthState authState) {
        mPrefsLock.lock();

        try {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            if (authState == null) {
                editor.remove(computeAuthStateKey());
            } else {
                editor.putString(computeAuthStateKey(), authState.jsonSerializeString());
            }

            if (!editor.commit()) {
                throw new IllegalStateException("Failed to write auth state into Shared Prefs");
            }
        } finally {
            mPrefsLock.unlock();
        }
    }

    private String computeAuthStateKey() {
        return KEY_AUTH_STATE + "_" + mProviderId;
    }
}
