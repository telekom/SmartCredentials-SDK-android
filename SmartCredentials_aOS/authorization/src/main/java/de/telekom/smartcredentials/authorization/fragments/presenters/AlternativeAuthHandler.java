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

package de.telekom.smartcredentials.authorization.fragments.presenters;

import java.lang.ref.WeakReference;

import de.telekom.smartcredentials.authorization.fingerprint.presenters.WeakRefClassResolver;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

public class AlternativeAuthHandler {

    public static final int ALTERNATIVE_AUTH_REQ_CODE = 1000;
    private static final String TAG = "AlternativeAuthHandler";
    private static final String AUTH_SUCCEEDED_MESSAGE = "Authorization succeeded";

    transient AuthorizationPluginCallback mPluginCallback;
    KeyguardManagerWrapper mKeyguardManagerWrapper;
    boolean mIsAuthorizedAlternativeMethod;
    private WeakReference<AlternativeAuthView> mView;

    public AlternativeAuthHandler(KeyguardManagerWrapper keyguardManagerWrapper) {
        mKeyguardManagerWrapper = keyguardManagerWrapper;
    }

    public void onAlternativeAuthResult(int requestCode, boolean isResultOk) {
        if (requestCode != ALTERNATIVE_AUTH_REQ_CODE) {
            return;
        }

        mIsAuthorizedAlternativeMethod = isResultOk;
        if (isResultOk) {
            ApiLoggerResolver.logInfo(AUTH_SUCCEEDED_MESSAGE);
            if (mPluginCallback != null) {
                mPluginCallback.onAuthorized();
            }
        } else {
            ApiLoggerResolver.logInfo(AuthorizationPluginError.AUTH_FAILED.getDesc());
            if (mPluginCallback != null) {
                mPluginCallback.onFailed(AuthorizationPluginError.AUTH_FAILED);
            }
        }
    }

    void viewReady(AlternativeAuthView view, AuthorizationPluginCallback pluginCallback) {
        mView = new WeakReference<>(view);
        mPluginCallback = pluginCallback;
    }

    void requestAlternativeAuthorization() {
        if (mKeyguardManagerWrapper.checkIsKeyguardSecured()) {
            new WeakRefClassResolver<AlternativeAuthView>() {
                @Override
                public void onWeakRefResolved(AlternativeAuthView ref) {
                    ref.showViewForAuthorization(mKeyguardManagerWrapper.getDeviceCredentialsIntent(),
                            ALTERNATIVE_AUTH_REQ_CODE);
                }

                @Override
                public void onNullRef() {
                    showError(AuthorizationPluginError.MISSING_VIEW);
                }
            }.execute(mView);
        } else {
            showError(AuthorizationPluginError.DEVICE_NOT_SECURED);
        }
    }

    void showError(AuthorizationPluginError authorizationPluginError) {
        if (mPluginCallback != null) {
            mPluginCallback.onFailed(authorizationPluginError);
        }
        ApiLoggerResolver.logError(TAG, authorizationPluginError.getDesc());
    }
}
