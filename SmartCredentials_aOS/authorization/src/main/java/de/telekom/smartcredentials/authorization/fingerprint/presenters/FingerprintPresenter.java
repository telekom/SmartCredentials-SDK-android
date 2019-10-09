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

package de.telekom.smartcredentials.authorization.fingerprint.presenters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.ref.WeakReference;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintCallback;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintManagerWrapper;
import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintAuthorizationPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintPresenter extends AuthPresenter<FingerprintView> implements AuthHandler.AuthorizationListener, FingerprintAuthorizationPresenter {

    private WeakReference<FingerprintView> mView;
    private FingerprintCallback mFingerprintCallback;
    private final FingerprintManagerWrapper mFingerprintManagerWrapper;

    public FingerprintPresenter(Context context, AuthHandler authHandler) {
        mFingerprintManagerWrapper = new FingerprintManagerWrapper(context);
        mAuthHandler = authHandler;
        init();
    }

    @Override
    public void viewReady(FingerprintView view) {
        mView = new WeakReference<>(view);
    }

    @Override
    public AuthorizationManagerWrapper getAuthorizationManagerWrapper() {
        return mFingerprintManagerWrapper;
    }
    //<editor-fold desc="Overridden methods">

    @Override
    public void onError(CharSequence errString) {
        super.onError(errString);

        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                ref.showFingerprintError(errString);
            }
        }.execute(mView);
    }

    @Override
    public void onFailed() {
        super.onFailed();

        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                ref.showFingerprintMismatched();
            }
        }.execute(mView);
    }

    @Override
    public void onHelp(CharSequence helpString) {
        super.onHelp(helpString);

        new WeakRefClassResolver<FingerprintView>() {
            @Override
            public void onWeakRefResolved(FingerprintView ref) {
                ref.showFingerprintHelp(helpString);
            }
        }.execute(mView);
    }

    public void setFingerprintCallback(FingerprintCallback fingerprintCallback) {
        mFingerprintCallback = fingerprintCallback;
    }

    public void onCancelButtonClicked() {
        if (mFingerprintCallback != null) {
            mFingerprintCallback.cancel();
        }
    }

    public void onAlternativeAuthButtonClicked() {
        if (mFingerprintCallback != null) {
            mFingerprintCallback.tryAlternativeAuthorization();
        }
    }
}
