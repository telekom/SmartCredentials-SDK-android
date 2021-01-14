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

package de.telekom.smartcredentials.authentication.controllers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;

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
import java.security.SecureRandom;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import de.telekom.smartcredentials.authentication.AuthClientConfiguration;
import de.telekom.smartcredentials.authentication.AuthStateManager;
import de.telekom.smartcredentials.authentication.AuthenticationStorageRepository;
import de.telekom.smartcredentials.authentication.AuthenticationTradeActivity;
import de.telekom.smartcredentials.authentication.converter.Converter;
import de.telekom.smartcredentials.authentication.di.ObjectGraphCreatorAuthentication;
import de.telekom.smartcredentials.authentication.model.SmartCredentialsAuthenticationTokenResponse;
import de.telekom.smartcredentials.authentication.parser.BundleTransformer;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.authentication.AuthenticationError;
import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;
import de.telekom.smartcredentials.core.authentication.AuthenticationTokenResponse;
import de.telekom.smartcredentials.core.authentication.OnFreshTokensRetrievedListener;
import de.telekom.smartcredentials.core.authentication.TokenRefreshListener;
import de.telekom.smartcredentials.core.authentication.configuration.AuthenticationConfiguration;
import de.telekom.smartcredentials.core.authentication.configuration.PkceConfiguration;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

/**
 * Created by Lucian Iacob on February 27, 2019.
 */
public class AuthenticationController implements AuthenticationApi {

    public static final String KEY_IDENTITY_PROVIDER_ID = "de.telekom.smartcredentials.auth.Provider";
    public static final String KEY_AUTH_CONFIG_FILE_ID = "de.telekom.smartcredentials.auth.ConfigFileId";
    public static final String KEY_COMPLETE_INTENT_CLASS = "de.telekom.smartcredentials.auth.CompleteIntentClass";
    public static final String KEY_CANCEL_INTENT_CLASS = "de.telekom.smartcredentials.auth.CancelIntentClass";
    public static final String KEY_CANCEL_INTENT_EXTRAS = "de.telekom.smartcredentials.auth.CancelIntentExtras";
    public static final String KEY_COMPLETE_INTENT_EXTRAS = "de.telekom.smartcredentials.auth.CompleteIntentExtras";
    private static final String TAG = "AuthenticationController";
    private static final AtomicReference<AuthenticationController> INSTANCE_REF =
            new AtomicReference<>(null);

    private AuthenticationStorageRepository mAuthenticationStorageRepository;
    private final AtomicReference<AuthorizationService> mAuthService = new AtomicReference<>();
    private final AtomicReference<AuthorizationRequest> mAuthRequest = new AtomicReference<>();
    private WeakReference<AuthenticationServiceInitListener> mAuthInitListener;
    private final AtomicReference<CustomTabsIntent> mAuthIntent = new AtomicReference<>();
    private AuthStateManager mAuthStateManager;
    private final AtomicReference<String> mClientId = new AtomicReference<>();
    private AuthClientConfiguration mConfiguration;
    private int mCustomTabColor;
    private ExecutorService mExecutor;
    private CoreController mCoreController;
    private PkceConfiguration mPkceConfiguration;

    private AuthenticationController(CoreController coreController) {
        mCoreController = coreController;
        mExecutor = Executors.newSingleThreadExecutor();
        mAuthenticationStorageRepository = ObjectGraphCreatorAuthentication.getInstance().provideAuthenticationStorageRepository();
    }

    public static AuthenticationController getInstance(CoreController coreController) {
        AuthenticationController authenticationService = INSTANCE_REF.get();
        if (authenticationService == null) {
            authenticationService = new AuthenticationController(coreController);
            INSTANCE_REF.set(authenticationService);
        }

        return authenticationService;
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public SmartCredentialsApiResponse<String> getUserInfoEndpointUri() {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getUserInfoEndpointUri");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        Uri userInfoEndpointUri = mConfiguration.getUserInfoEndpointUri();
        if (userInfoEndpointUri != null) {
            return new SmartCredentialsResponse<>(userInfoEndpointUri.toString());
        }

        AuthorizationServiceConfiguration authServiceConfig;
        AuthorizationServiceDiscovery discoveryDoc;

        if ((authServiceConfig =
                mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration()) != null
                && (discoveryDoc = authServiceConfig.discoveryDoc) != null
                && (userInfoEndpointUri = discoveryDoc.getUserinfoEndpoint()) != null) {
            return new SmartCredentialsResponse<>(userInfoEndpointUri.toString());
        }

        return new SmartCredentialsResponse<>(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> initialize(AuthenticationConfiguration configuration,
                                                           AuthenticationServiceInitListener listener) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "initialize");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (!browserExists(configuration.getContext())) {
            listener.onFailed(AuthenticationError.NO_INSTALLED_BROWSERS);
            return new SmartCredentialsResponse<>(false);
        }

        mAuthenticationStorageRepository.saveAuthConfigValue(KEY_IDENTITY_PROVIDER_ID, configuration.getIdentityProviderId());
        mAuthenticationStorageRepository.saveAuthConfigValue(KEY_AUTH_CONFIG_FILE_ID, String.valueOf(configuration.getAuthConfigFileResId()));

        mCustomTabColor = configuration.getCustomTabBarColor();
        mAuthInitListener = new WeakReference<>(listener);
        if (mExecutor == null || mExecutor.isShutdown()) {
            mExecutor = Executors.newSingleThreadExecutor();
        }
        mExecutor.submit(() -> doInit(configuration.getContext(), configuration.getIdentityProviderId(),
                configuration.getAuthConfigFileResId(), configuration.getPkceConfiguration()));
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> login(Context context, Intent completionIntent, Intent cancelIntent) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "login");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mConfiguration.hasConfigurationChanges()) {
            ApiLoggerResolver.logError(TAG, "Could not start login. Configuration has changed");
            return new SmartCredentialsResponse<>(false);
        }
        if (mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() == null) {
            ApiLoggerResolver.logError(TAG, "AuthenticationController must be " +
                    "initialized first. Login aborted");
            return new SmartCredentialsResponse<>(false);
        }

        saveIntentEndpointsToPrefs(completionIntent, cancelIntent);

        PendingIntent intermediateIntent = AuthenticationTradeActivity
                .createStartIntent(context.getApplicationContext());

        PendingIntent cancelPendingIntent = PendingIntent.getActivity(context, 0, cancelIntent, 0);
        mExecutor.submit(() -> doLogin(intermediateIntent, cancelPendingIntent));
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> performActionWithFreshTokens(OnFreshTokensRetrievedListener listener) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "performActionWithFreshTokens");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        AuthState authState = mAuthStateManager.getCurrent();
        authState.performActionWithFreshTokens(
                mAuthService.get(),
                (accessToken, idToken, ex) -> {
                    mAuthStateManager.updateAfterActionWithFreshTokens(authState);
                    listener.onRefreshComplete(accessToken, idToken, Converter.convert(ex));
                });
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> logOut() {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "logOut");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        AuthState currentState = mAuthStateManager.getCurrent();
        AuthState clearState = new AuthState(currentState.getAuthorizationServiceConfiguration());
        if (currentState.getLastRegistrationResponse() != null) {
            clearState.update(currentState.getLastRegistrationResponse());
        }

        mAuthStateManager.replace(clearState);
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> isUserLoggedIn() {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "isUserLoggedIn");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return new SmartCredentialsResponse<>(mAuthStateManager.getCurrent().isAuthorized()
                && !mConfiguration.hasConfigurationChanges()
                && mAuthStateManager.getCurrent().getAuthorizationServiceConfiguration() != null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> refreshAccessToken(TokenRefreshListener<AuthenticationTokenResponse> listener) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "refreshAccessToken");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mAuthStateManager.getCurrent().getRefreshToken() == null) {
            listener.onFailed(AuthenticationError.ERROR_NULL_REFRESH_TOKEN);
            return new SmartCredentialsResponse<>(false);
        }

        ClientAuthentication clientAuthentication;
        try {
            clientAuthentication = mAuthStateManager.getCurrent().getClientAuthentication();
        } catch (ClientAuthentication.UnsupportedAuthenticationMethod unsupportedAuthenticationMethod) {
            listener.onFailed(AuthenticationError.UNSUPPORTED_CLIENT_AUTH);
            return new SmartCredentialsResponse<>(false);
        }

        mAuthService.get().performTokenRequest(
                mAuthStateManager.getCurrent().createTokenRefreshRequest(),
                clientAuthentication,
                (response, exception) -> {
                    mAuthStateManager.updateAfterTokenResponse(response, exception);
                    SmartCredentialsAuthenticationTokenResponse tokenResponse = Converter.convert(response);
                    listener.onTokenRequestCompleted(tokenResponse, Converter.convert(exception));
                });
        return new SmartCredentialsResponse<>(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> destroy() {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "destroy");

        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHENTICATION)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHENTICATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mAuthService.get() != null) {
            mAuthService.get().dispose();
            mAuthService.set(null);
        }
        mExecutor.shutdownNow();
        return new SmartCredentialsResponse<>(true);
    }

    private void doInit(Context context, String identityProviderId, int authConfigFileResId,
                        PkceConfiguration pkceConfiguration) {
        mAuthStateManager = AuthStateManager.getInstance(context, identityProviderId);
        mConfiguration = AuthClientConfiguration.getInstance(context, authConfigFileResId,
                identityProviderId);
        extractPkceConfig(pkceConfiguration);
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

        if (mPkceConfiguration.getCodeVerifier() != null) {
            ApiLoggerResolver.logInfo("Using PKCE Extension.");
            if (mPkceConfiguration.getCodeChallenge() == null || mPkceConfiguration.getCodeChallengeMethod() == null) {
                authRequestBuilder.setCodeVerifier(mPkceConfiguration.getCodeVerifier());
            } else {
                authRequestBuilder.setCodeVerifier(mPkceConfiguration.getCodeVerifier(),
                        mPkceConfiguration.getCodeChallenge(), mPkceConfiguration.getCodeChallengeMethod());
            }
        }

        mAuthRequest.set(authRequestBuilder.build());
        if (mAuthInitListener.get() != null) {
            mAuthInitListener.get().onSuccess();
        }
    }

    private void saveIntentEndpointsToPrefs(final Intent completionIntent, final Intent cancelIntent) {
        mAuthenticationStorageRepository.saveAuthConfigValue(KEY_COMPLETE_INTENT_CLASS, completionIntent.getComponent().getClassName());
        mAuthenticationStorageRepository.saveAuthConfigValue(KEY_CANCEL_INTENT_CLASS, cancelIntent.getComponent().getClassName());

        final Bundle completionExtras = completionIntent.getExtras();
        if (completionExtras != null) {
            final String encodedExtras = BundleTransformer.encodeToString(completionExtras);
            mAuthenticationStorageRepository.saveAuthConfigValue(KEY_COMPLETE_INTENT_EXTRAS, encodedExtras);
        }

        final Bundle cancelExtras = completionIntent.getExtras();
        if (cancelExtras != null) {
            final String encodedExtras = BundleTransformer.encodeToString(cancelExtras);
            mAuthenticationStorageRepository.saveAuthConfigValue(KEY_CANCEL_INTENT_EXTRAS, encodedExtras);
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

    private void extractPkceConfig(PkceConfiguration pkceConfiguration) {
        if (pkceConfiguration != null) {
            if (pkceConfiguration.getCodeVerifier() == null) {
                mPkceConfiguration = new PkceConfiguration(generateCodeVerifier());
            } else {
                mPkceConfiguration = pkceConfiguration;
            }
        } else {
            mPkceConfiguration = new PkceConfiguration();
        }
    }

    private String generateCodeVerifier() {
        ApiLoggerResolver.logInfo("Using library generated PKCE code verifier.");
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        return Base64.encodeToString(code, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
    }
}
