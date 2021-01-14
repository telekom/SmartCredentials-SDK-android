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

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.AuthorizationPresenter;

@RequiresApi(api = Build.VERSION_CODES.M)
public abstract class AuthPresenter<T> implements AuthHandler.AuthorizationListener, AuthorizationPresenter<T> {

    AuthorizationManagerWrapper mAuthorizationManagerWrapper;
    protected AuthHandler mAuthHandler;
    private boolean mIsAuthorized;
    private transient AuthorizationPluginCallback mPluginCallback;

    public void init() {
        mAuthorizationManagerWrapper = getAuthorizationManagerWrapper();
        mAuthHandler.init(mAuthorizationManagerWrapper);
    }

    @Override
    public void onAuthenticated() {
        mIsAuthorized = true;

        if (mPluginCallback != null) {
            mPluginCallback.onAuthorized();
        }
    }

    @Override
    public void onPermissionMissing() {
        if (mPluginCallback != null) {
            mPluginCallback.onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
        }
    }

    @Override
    public void onError(CharSequence errString) {
        mIsAuthorized = false;
        if (mPluginCallback != null) {
            AuthorizationPluginError error = AuthorizationPluginError.map(errString.toString());
            mPluginCallback.onFailed(error != null ? error : AuthorizationPluginError.AUTH_FAILED);
        }
    }

    @Override
    public void onError(@NonNull AuthorizationPluginError error) {
        mIsAuthorized = false;
        if (mPluginCallback != null) {
            mPluginCallback.onFailed(error);
        }
    }

    @Override
    public void onFailed() {
        mIsAuthorized = false;
    }

    @Override
    public void onHelp(CharSequence helpString) {
        mIsAuthorized = false;
    }
    //</editor-fold>

    public void setAuthPluginCallback(AuthorizationPluginCallback pluginCallback) {
        mPluginCallback = pluginCallback;
    }

    @Override
    public void startListeningForUserAuth() {
        mAuthHandler.setListener(this);
        mAuthHandler.tryAuthenticateUser();
    }

    public boolean isAuthorized() {
        return mIsAuthorized;
    }

    @Override
    public boolean isFeatureAvailable() {
        return mAuthHandler.isFingerprintAuthAvailable();
    }

    @Override
    public void stopListeningForUserAuth() {
        mAuthHandler.setListener(null);
        mAuthHandler.cancelAuthentication();
    }

    @Override
    public boolean isUserAuthCapable() {
        return mAuthorizationManagerWrapper != null
                && mAuthorizationManagerWrapper.isUserAuthCapable();
    }

    public abstract AuthorizationManagerWrapper getAuthorizationManagerWrapper();
}
