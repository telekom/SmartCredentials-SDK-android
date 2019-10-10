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

package de.telekom.smartcredentials.authorization.controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import de.telekom.smartcredentials.authorization.biometric.BiometricPresenter;
import de.telekom.smartcredentials.authorization.biometric.BiometricPromptWrapper;
import de.telekom.smartcredentials.authorization.callback.PluginCallbackAuthorizationConverter;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintManagerWrapper;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.AuthPresenterFactory;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.FingerprintPresenter;
import de.telekom.smartcredentials.authorization.fragments.AuthFragmentFactory;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintAuthorizationPresenter;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

import static de.telekom.smartcredentials.core.utils.ApiLevel.API_ABOVE_28_ERROR_MESSAGE;
import static de.telekom.smartcredentials.core.utils.ApiLevel.API_BELOW_23_ERROR_MESSAGE;
import static de.telekom.smartcredentials.core.utils.ApiLevel.API_BELOW_28_ERROR_MESSAGE;
import static de.telekom.smartcredentials.core.utils.ApiLevel.isAbove28;
import static de.telekom.smartcredentials.core.utils.ApiLevel.isBelow23;
import static de.telekom.smartcredentials.core.utils.ApiLevel.isBelow28;

public class AuthorizationController implements AuthorizationApi {

    private final Context mContext;
    private final CoreController mCoreController;
    private final AuthHandler mAuthHandler;
    private final FingerprintManagerWrapper mFingerprintManagerWrapper;
    private final BiometricPromptWrapper mBiometricPromptWrapper;
    private final KeyguardManagerWrapper mKeyguardManagerWrapper;

    public AuthorizationController(Context context, CoreController coreController,
                                   AuthHandler authHandler,
                                   FingerprintManagerWrapper fingerprintManagerWrapper,
                                   BiometricPromptWrapper biometricPromptWrapper,
                                   KeyguardManagerWrapper keyguardManagerWrapper) {
        mContext = context;
        mCoreController = coreController;
        mAuthHandler = authHandler;
        mFingerprintManagerWrapper = fingerprintManagerWrapper;
        mBiometricPromptWrapper = biometricPromptWrapper;
        mKeyguardManagerWrapper = keyguardManagerWrapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Fragment> getAuthorizeUserFragment(@NonNull AuthorizationCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getAuthorizeUserFragment");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }
        if (isAbove28()) {
            return new SmartCredentialsResponse<>(new Throwable(API_ABOVE_28_ERROR_MESSAGE));
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTH_POP_UP)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTH_POP_UP.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        return new SmartCredentialsResponse<>(AuthFragmentFactory
                .getInstance(mFingerprintManagerWrapper, mKeyguardManagerWrapper)
                .getAuthorizationFragment(getAuthorizationPluginCallback(callback)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<FingerprintAuthorizationPresenter> getFingerprintAuthorizationPresenter(@NonNull AuthorizationCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getFingerprintAuthorizationPresenter");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }
        if (isBelow23()) {
            return new SmartCredentialsResponse<>(new Throwable(API_BELOW_23_ERROR_MESSAGE));
        }
        if (isAbove28()) {
            return new SmartCredentialsResponse<>(new Throwable(API_ABOVE_28_ERROR_MESSAGE));
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTH_CUSTOM_UI)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTH_CUSTOM_UI.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        FingerprintPresenter presenter = AuthPresenterFactory
                .getInstance()
                .getFingerprintPresenter(mContext, mAuthHandler);
        presenter.setAuthPluginCallback(getAuthorizationPluginCallback(callback));
        return new SmartCredentialsResponse<>(presenter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<BiometricAuthorizationPresenter> getBiometricAuthorizationPresenter(@NonNull AuthorizationCallback callback) {

        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getBiometricAuthorizationPresenter");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (isBelow28()) {
            return new SmartCredentialsResponse<>(new Throwable(API_BELOW_28_ERROR_MESSAGE));
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTH_BIOMETRICS)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTH_BIOMETRICS.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        BiometricPresenter presenter = AuthPresenterFactory
                .getInstance()
                .getBiometricPresenter(mAuthHandler, mBiometricPromptWrapper);
        presenter.setAuthPluginCallback(getAuthorizationPluginCallback(callback));

        return new SmartCredentialsResponse<>(presenter);
    }

    private AuthorizationPluginCallback getAuthorizationPluginCallback(AuthorizationCallback callback) {
        return PluginCallbackAuthorizationConverter
                .convertToDomainPluginCallback(callback, getClass().getSimpleName());
    }
}
