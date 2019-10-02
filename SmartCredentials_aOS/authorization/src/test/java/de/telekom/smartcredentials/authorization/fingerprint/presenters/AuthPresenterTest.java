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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class AuthPresenterTest {

    private AuthorizationPluginCallback mPluginCallback;
    private AuthPresenter mAuthPresenter;
    private AuthHandler mAuthHandler;

    @Before
    public void setUp() {
        mPluginCallback = Mockito.mock(AuthorizationPluginCallback.class);
        mAuthPresenter = new AuthPresenter() {
            @Override
            public void viewReady(Object view) {

            }

            @Override
            public AuthorizationManagerWrapper getAuthorizationManagerWrapper() {
                return Mockito.mock(AuthorizationManagerWrapper.class);
            }
        };
        mAuthPresenter.setAuthPluginCallback(mPluginCallback);
        mAuthHandler = Mockito.mock(AuthHandler.class);
        mAuthPresenter.mAuthHandler = mAuthHandler;
    }

    @Test
    public void initCallsInitOnAuthHandler() {
        mAuthPresenter.init();

        verify(mAuthHandler).init(mAuthPresenter.mAuthorizationManagerWrapper);
    }

    @Test
    public void onAuthenticatedCallsPluginCallbackMethod() {
        mAuthPresenter.onAuthenticated();

        assertTrue(mAuthPresenter.isAuthorized());
        verify(mPluginCallback).onAuthorized();
    }

    @Test
    public void onPermissionMissingCallsMethodOnPluginCallback() {
        mAuthPresenter.onPermissionMissing();
        verify(mPluginCallback).onPluginUnavailable(AuthorizationPluginUnavailable.FINGERPRINT_PERMISSION_MISSING);
    }

    @Test
    public void onErrorCallsMethodOnPluginCallbackWithGenericFailureMessage() {
        CharSequence sequence = "error";
        mAuthPresenter.onError(sequence);

        assertFalse(mAuthPresenter.isAuthorized());
        verify(mPluginCallback).onFailed(AuthorizationPluginError.AUTH_FAILED);
    }


    @Test
    public void onErrorCallsMethodOnPluginCallbackWithMappedFailureMessage() {
        AuthorizationPluginError authCanceled = AuthorizationPluginError.AUTH_CANCELED_BY_USER;
        CharSequence sequence = authCanceled.getDesc();
        mAuthPresenter.onError(sequence);

        assertFalse(mAuthPresenter.isAuthorized());
        verify(mPluginCallback).onFailed(authCanceled);
    }

    @Test
    public void onErrorCallsMethodOnPluginCallbackWithReceivedMessage() {
        AuthorizationPluginError authCanceled = AuthorizationPluginError.AUTH_CANCELED_BY_USER;
        mAuthPresenter.onError(authCanceled);

        assertFalse(mAuthPresenter.isAuthorized());
        verify(mPluginCallback).onFailed(authCanceled);
    }

    @Test
    public void onFailedSetsIsAuthorizedValue() {
        mAuthPresenter.onFailed();

        assertFalse(mAuthPresenter.isAuthorized());
    }

    @Test
    public void onHelpSetsIsAuthorizedValue() {
        mAuthPresenter.onHelp(null);

        assertFalse(mAuthPresenter.isAuthorized());
    }

    @Test
    public void startListeningForUserAuthSetsListenerAndStartAuthorizationAttempts() {
        mAuthPresenter.startListeningForUserAuth();

        verify(mAuthHandler).setListener(mAuthPresenter);
        verify(mAuthHandler).tryAuthenticateUser();
    }

    @Test
    public void isFeatureAvailableCallsMethodOnAuthHandler() {
        mAuthPresenter.isFeatureAvailable();

        verify(mAuthHandler).isFingerprintAuthAvailable();
    }

    @Test
    public void stopListeningForUserAuthResetsListenerAndCancelsAuthorizationAttempts() {
        mAuthPresenter.stopListeningForUserAuth();

        verify(mAuthHandler).setListener(null);
        verify(mAuthHandler).cancelAuthentication();
    }

    @Test
    public void isUserAuthCapableChecksForPermissionAndHardware() {
        mAuthPresenter.init();
        when(mAuthPresenter.mAuthorizationManagerWrapper.hasPermission()).thenReturn(true);

        mAuthPresenter.isUserAuthCapable();

        verify(mAuthPresenter.mAuthorizationManagerWrapper).isUserAuthCapable();
    }
}