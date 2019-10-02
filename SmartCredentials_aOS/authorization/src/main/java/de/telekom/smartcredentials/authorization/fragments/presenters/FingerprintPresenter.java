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

import de.telekom.smartcredentials.authorization.fingerprint.FingerprintCallback;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.WeakRefClassResolver;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

public class FingerprintPresenter extends AlternativeAuthHandler implements FingerprintCallback {

    private WeakReference<FingerprintView> mView;
    private Stage mStage = Stage.FINGERPRINT;

    public FingerprintPresenter(KeyguardManagerWrapper keyguardManagerWrapper) {
        super(keyguardManagerWrapper);
        mKeyguardManagerWrapper = keyguardManagerWrapper;
    }

    @Override
    public void tryAlternativeAuthorization() {
        ApiLoggerResolver.logInfo("Alternative authorization selected");
        if (mStage == Stage.FINGERPRINT) {
            requestAlternativeAuthorization();
        }
    }

    @Override
    public void onError() {
        ApiLoggerResolver.logInfo("Fingerprint authorization error");
        requestAlternativeAuthorization();
    }

    @Override
    public void cancel() {
        ApiLoggerResolver.logInfo(AuthorizationPluginError.AUTH_CANCELED_BY_USER.getDesc());
        dismissView();
        mPluginCallback.onFailed(AuthorizationPluginError.AUTH_CANCELED_BY_USER);
    }

    public void viewReady(FingerprintView fingerprintView, AuthorizationPluginCallback pluginCallback) {
        super.viewReady(fingerprintView, pluginCallback);
        mView = new WeakReference<>(fingerprintView);
        authorize();
    }

    public void viewResumed() {
        if (mStage == Stage.FINGERPRINT) {
            new WeakRefClassResolver<FingerprintView>() {
                @Override
                public void onWeakRefResolved(FingerprintView ref) {
                    ref.listenToFingerprintAuthorization();
                }
            }.execute(mView);
        }
    }

    public void onAlternativeAuthResult(int requestCode, boolean isResultOk) {
        dismissView();
        super.onAlternativeAuthResult(requestCode, isResultOk);
    }

    public void viewDestroyed() {
        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                if (!ref.isAuthorized() && !mIsAuthorizedAlternativeMethod) {
                    mPluginCallback.onFailed(AuthorizationPluginError.AUTH_BECAME_UNAVAILABLE);
                }
            }
        }.execute(mView);
    }

    private void authorize() {
        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                if (ref.isFeatureAvailable()) {
                    ApiLoggerResolver.logInfo("Authorizing with fingerprint");
                    ref.showFingerprintView();
                } else {
                    ApiLoggerResolver.logInfo("Authorizing with device credentials");
                    mStage = Stage.OTHER;
                    requestAlternativeAuthorization();
                }
            }
        }.execute(mView);
    }

    private void dismissView() {
        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                ref.remove();
            }
        }.execute(mView);
    }

}
