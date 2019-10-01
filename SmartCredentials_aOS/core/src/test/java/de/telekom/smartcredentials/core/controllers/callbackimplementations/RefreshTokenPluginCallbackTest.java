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

package de.telekom.smartcredentials.core.controllers.callbackimplementations;

import org.junit.Before;
import org.junit.Test;

import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.domain.utils.MocksProvider;

import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class RefreshTokenPluginCallbackTest {

    private RefreshTokenPluginCallback mRefreshTokenPluginCallback;

    private AuthenticationPluginCallback mMockedAuthenticationPluginCallback;

    @Before
    public void setUp() {
        mMockedAuthenticationPluginCallback = MocksProvider.provideAuthPluginCallback();
        mRefreshTokenPluginCallback = new RefreshTokenPluginCallback(mMockedAuthenticationPluginCallback);
    }

    @Test
    public void getRefreshTokenPluginCallbackCallsPluginCallbackOnAuthenticatedNewItemIsReceived() {
        String refreshToken = "123456789";
        TokenResponse token = new TokenResponse(refreshToken, -1);

        mRefreshTokenPluginCallback.onSuccess(token);

        verify(mMockedAuthenticationPluginCallback).onAuthenticated(token);
    }

    @Test
    public void getRefreshTokenPluginCallbackCallsInitialPluginCallbackOnFailedWhenRefreshPluginCallbackOnFailedIsCalled() {
        String failureMessage = "something went wrong";

        mRefreshTokenPluginCallback.onFailed(failureMessage);

        verify(mMockedAuthenticationPluginCallback).onRetrievingAuthInfoFailed(failureMessage);
    }

}