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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.AuthorizationServiceDiscovery;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretBasic;
import net.openid.appauth.RegistrationRequest;
import net.openid.appauth.RegistrationResponse;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.browser.BrowserSelector;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import de.telekom.smartcredentials.authentication.converter.Converter;
import de.telekom.smartcredentials.authentication.model.SmartCredentialsAuthenticationTokenResponse;
import de.telekom.smartcredentials.authentication.parser.BundleTransformer;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationService;
import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;
import de.telekom.smartcredentials.core.authentication.OnFreshTokensRetrievedListener;
import de.telekom.smartcredentials.core.authentication.TokenRefreshListener;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.authentication.AuthClientConfiguration.CONFIG_PREFS_NAME;

/**
 * Created by Lucian Iacob on February 27, 2019.
 */
public class SmartCredentialsAuthenticationService implements AuthenticationService {

    static final String KEY_IDENTITY_PROVIDER_ID = "de.telekom.smartcredentials.auth.Provider";
    static final String KEY_AUTH_CONFIG_FILE_ID = "de.telekom.smartcredentials.auth.ConfigFileId";
    static final String KEY_COMPLETE_INTENT_CLASS = "de.telekom.smartcredentials.auth.CompleteIntentClass";
    static final String KEY_CANCEL_INTENT_CLASS = "de.telekom.smartcredentials.auth.CancelIntentClass";
    static final String KEY_CANCEL_INTENT_EXTRAS = "de.telekom.smartcredentials.auth.CancelIntentExtras";
    static final String KEY_COMPLETE_INTENT_EXTRAS = "de.telekom.smartcredentials.auth.CompleteIntentExtras";
    private static final String TAG = "SmartCredentialsAuthenticationService";
    private static final AtomicReference<SmartCredentialsAuthenticationService> INSTANCE_REF =
            new AtomicReference<>(null);

    private final AtomicReference<AuthorizationService> mAuthService = new AtomicReference<>();
    private final AtomicReference<AuthorizationRequest> mAuthRequest = new AtomicReference<>();
    private WeakReference<AuthenticationServiceInitListener> mAuthInitListener;
    private final AtomicReference<CustomTabsIntent> mAuthIntent = new AtomicReference<>();
    private AuthStateManager mAuthStateManager;
    private final AtomicReference<String> mClientId = new AtomicReference<>();
    private AuthClientConfiguration mConfiguration;
    private int mCustomTabColor;
    private ExecutorService mExecutor;

    private SmartCredentialsAuthenticationService() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public static SmartCredentialsAuthenticationService getInstance() {
        SmartCredentialsAuthenticationService authenticationService = INSTANCE_REF.get();
        if (authenticationService == null) {
            authenticationService = new SmartCredentialsAuthenticationService();
            INSTANCE_REF.set(authenticationService);
        }

        return authenticationService;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public String getUserInfoEndpointUri() {
        Uri userInfoEndpointUri = mConfiguration.getUserInfoEndpointUri();
        if (userInfoEndpointUri != null) {
            return userInfoEndpointUri.toString();
        }

        AuthorizationServiceConfiguration authServiceConfig;
        AuthorizationServiceDiscovery discoveryDoc;

        if ((authServiceConfig =
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration()) != null
                && (discoveryDoc = authServiceConfig.discoveryDoc) != null
                && (userInfoEndpointUri = discoveryDoc.getUserinfoEndpoint()) != null) {
            return userInfoEndpointUri.toString();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(Context context,
                           String identityProviderId,
                           int authConfigFileResId,
                           int customTabBarColor,
                           AuthenticationServiceInitListener listener) {

        if (!browserExists(context)) {
            listener.onFailed(AuthenticationError.NO_INSTALLED_BROWSERS);
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(CONFIG_PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_IDENTITY_PROVIDER_ID, identityProviderId).apply();
        prefs.edit().putInt(KEY_AUTH_CONFIG_FILE_ID, authConfigFileResId).apply();
        mCustomTabColor = customTabBarColor;
        mAuthInitListener = new WeakReference<>(listener);
        if (mExecutor == null || mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(() -> doInit(context, identityProviderId, authConfigFileResId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(Context context, Intent completionIntent, Intent cancelIntent) {
        if (mConfiguration.hasConfigurationChanges()) {
            ApiLoggerResolver.logError(TAG, "Could not start login. Configuration has changed");
            return;
        }
        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() == null) {
            ApiLoggerResolver.logError(TAG, "SmartCredentialsAuthenticationService must be " +
                    "initialized first. Login aborted");
            return;
        }

        SharedPreferences prefs = context.getSharedPreferences(CONFIG_PREFS_NAME, Context.MODE_PRIVATE);

        saveIntentEndpointsToPrefs(prefs, completionIntent, cancelIntent);

        PendingIntent intermediateIntent = AuthenticationTradeActivity
                .createStartIntent(context.getApplicationContext());

        PendingIntent cancelPendingIntent = PendingIntent.getActivity(context, 0, cancelIntent, 0);
        mExecutor.submit(() -> doLogin(intermediateIntent, cancelPendingIntent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void performActionWithFreshTokens(OnFreshTokensRetrievedListener listener) {
        mAuthStateManager.getCurrent().performActionWithFreshTokens(
                mAuthService.get(),
                (accessToken, idToken, ex) -> listener
                        .onRefreshComplete(accessToken, idToken, Converter.convert(ex)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logOut() {
        AuthState currentState = mAuthStateManager.getCurrent();
        AuthState clearState = new AuthState(currentState.getAuthorizationServiceConfiguration());
        if (currentState.getLastRegistrationResponse() != null) {
            clearState.update(currentState.getLastRegistrationResponse());
        }

        mAuthStateManager.replace(clearState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUserLoggedIn() {
        return mAuthStateManager.getCurrent().isAuthorized()
                && !mConfiguration.hasConfigurationChanges()
                && mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshAccessToken(TokenRefreshListener listener) {
        if (mAuthStateManager.getCurrent().getRefreshToken() == null) {
            listener.onFailed(AuthenticationError.ERROR_NULL_REFRESH_TOKEN);
            return;
        }

        ClientAuthentication clientAuthentication;
        try {
            clientAuthentication = mAuthStateManager.getCurrent().getClientAuthentication();
        } catch (ClientAuthentication.UnsupportedAuthenticationMethod unsupportedAuthenticationMethod) {
            listener.onFailed(AuthenticationError.UNSUPPORTED_CLIENT_AUTH);
            return;
        }

        mAuthService.get().performTokenRequest(
                mAuthStateManager.getCurrent().createTokenRefreshRequest(),
                clientAuthentication,
                (response, exception) -> {
                    mAuthStateManager.updateAfterTokenResponse(response, exception);
                    SmartCredentialsAuthenticationTokenResponse tokenResponse = Converter.convert(response);
                    listener.onTokenRequestCompleted(tokenResponse, Converter.convert(exception));
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        if (mAuthService.get() != null) {
            mAuthService.get().dispose();
            mAuthService.set(null);
        }
        mExecutor.shutdownNow();
    }

    private void doInit(Context context, String identityProviderId, int authConfigFileResId) {
        mAuthStateManager = AuthStateManager.getInstance(context, identityProviderId);
        mConfiguration = AuthClientConfiguration.getInstance(context, authConfigFileResId, identityProviderId);
        recreateAuthService(context);

        if (mConfiguration.hasConfigurationChanges()) {
            mAuthStateManager.replace(new AuthState());
            if (!mConfiguration.isValid()) {
                ApiLoggerResolver.logError(TAG, mConfiguration.getConfigError());
                if (mAuthInitListener.get() != null) {
                    mAuthInitListener.get().onFailed(AuthenticationError.INVALID_DISCOVERY_DOCUMENT);
                }
                return;
            }
            mConfiguration.acceptConfiguration();
        }

        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null) {
            initializeClient();
            return;
        }

        if (mConfiguration.getDiscoveryUri() == null) {
            ApiLoggerResolver.logInfo("Creating auth config from resources");
            AuthorizationServiceConfiguration configuration = new AuthorizationServiceConfiguration(
                    mConfiguration.getAuthEndpointUri(),
                    mConfiguration.getTokenEndpointUri(),
                    mConfiguration.getRegistrationEndpointUri());

            mAuthStateManager.replace(new AuthState(configuration));
            initializeClient();
            return;
        }

        AuthorizationServiceConfiguration.fetchFromUrl(
                mConfiguration.getDiscoveryUri(),
                this::handleConfigurationRetrievalResult);
    }

    private void createAuthRequest() {
        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                        mClientId.get(),
                        ResponseTypeValues.CODE,
                        mConfiguration.getRedirectUri())
                        .setScope(mConfiguration.getScope());

        mAuthRequest.set(authRequestBuilder.build());
        if (mAuthInitListener.get() != null) {
            mAuthInitListener.get().onSuccess();
        }
    }

    private void saveIntentEndpointsToPrefs(final SharedPreferences prefs, final Intent completionIntent,
                                            final Intent cancelIntent) {
        prefs.edit().putString(KEY_COMPLETE_INTENT_CLASS, completionIntent.getComponent().getClassName()).apply();
        prefs.edit().putString(KEY_CANCEL_INTENT_CLASS, cancelIntent.getComponent().getClassName()).apply();

        final Bundle completionExtras = completionIntent.getExtras();
        if (completionExtras != null) {
            final String encodedExtras = BundleTransformer.encodeToString(completionExtras);
            prefs.edit().putString(KEY_COMPLETE_INTENT_EXTRAS, encodedExtras).apply();
        }

        final Bundle cancelExtras = completionIntent.getExtras();
        if (cancelExtras != null) {
            final String encodedExtras = BundleTransformer.encodeToString(cancelExtras);
            prefs.edit().putString(KEY_CANCEL_INTENT_EXTRAS, encodedExtras).apply();
        }
    }

    private void recreateAuthService(Context context) {
        if (mAuthService.get() != null) {
            mAuthService.get().dispose();
            mAuthService.set(null);
        }

        mAuthService.set(new AuthorizationService(context, AppAuthConfiguration.DEFAULT));
        mAuthRequest.set(null);
        mAuthIntent.set(null);
    }

    private void doLogin(PendingIntent completionIntent, PendingIntent cancelIntent) {
        AuthorizationRequest authRequest = mAuthRequest.get();
        warmUpBrowser(authRequest.toUri());
        mAuthService.get().performAuthorizationRequest(
                authRequest,
                completionIntent,
                cancelIntent,
                mAuthIntent.get());
    }

    private void handleConfigurationRetrievalResult(
            AuthorizationServiceConfiguration authServiceConfiguration,
            AuthorizationException ex) {
        if (authServiceConfiguration == null) {
            if (mAuthInitListener.get() != null) {
                mAuthInitListener.get()
                        .onFailed(AuthenticationError.ERROR_RETRIEVING_DISCOVERY_DOCUMENT);
            }
            return;
        }

        mAuthStateManager.replace(new AuthState(authServiceConfiguration));
        mExecutor.submit(this::initializeClient);
    }

    private void warmUpBrowser(Uri uri) {
        CustomTabsIntent.Builder builder = mAuthService.get().createCustomTabsIntentBuilder(uri);
        builder.setToolbarColor(mCustomTabColor);
        mAuthIntent.set(builder.build());
    }

    private void handleRegistrationResponse(RegistrationResponse registrationResponse,
                                            AuthorizationException ex) {
        mAuthStateManager.updateAfterRegistration(registrationResponse, ex);
        if (registrationResponse == null) {
            if (mAuthInitListener.get() != null) {
                mAuthInitListener.get().onFailed(AuthenticationError.DYNAMIC_REGISTRATION_FAILED);
            }
            return;
        }

        mClientId.set(registrationResponse.clientId);
        createAuthRequest();
    }

    private void initializeClient() {
        if (mConfiguration.getClientId() != null) {
            ApiLoggerResolver.logInfo("Using static client ID: " + mConfiguration.getClientId());
            mClientId.set(mConfiguration.getClientId());
            createAuthRequest();
            return;
        }

        RegistrationResponse lastResponse =
                mAuthStateManager.getCurrent().getLastRegistrationResponse();
        if (lastResponse != null) {
            ApiLoggerResolver.logInfo("Using dynamic client ID: " + lastResponse.clientId);
            mClientId.set(lastResponse.clientId);
            createAuthRequest();
            return;
        }

        RegistrationRequest registrationRequest =
                new RegistrationRequest.Builder(
                        mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration(),
                        Collections.singletonList(mConfiguration.getRedirectUri()))
                        .setTokenEndpointAuthenticationMethod(ClientSecretBasic.NAME)
                        .build();

        mAuthService.get().performRegistrationRequest(
                registrationRequest,
                this::handleRegistrationResponse);
    }

    private boolean browserExists(Context context) {
        return !BrowserSelector.getAllBrowsers(context).isEmpty();
    }
}
