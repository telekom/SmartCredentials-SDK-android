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

package de.telekom.smartcredentials.qrlogin.qr;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import de.telekom.smartcredentials.core.networking.AuthParamKey;
import de.telekom.smartcredentials.core.networking.ServerSocket;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class QrServerPluginTest {

    private final String mBrowserClient = "QRServerSocket";
    private final String mQrId = "1234567";
    private final String mIdTokenParam = "id_token_parameter";
    private final String mRefreshToken = "refresh_token_parameter";
    private final String mQrParam =
            "{\"mBrowserClient\":\"" + mBrowserClient + "\",\"qr_uuid\":\"" + mQrId + "\"}";

    @SuppressWarnings("unused")
    private String mParams = "{"
            + "\"" + AuthParamKey.QR_CODE.name() + "\":" + mQrParam + ", "
            + "\"" + AuthParamKey.ID_TOKEN.name() + "\":\"" + mIdTokenParam + "\", "
            + "\"" + AuthParamKey.REFRESH_TOKEN.name() + "\":\"" + mRefreshToken + "\"}";

    private Map<String, String> requestParameters;

    private ServerSocket mServerSocket;


    @Before
    public void setUp() {
        mServerSocket = Mockito.mock(ServerSocket.class);
        requestParameters = generateRequestParamsMap();
    }

    @Test
    public void getAccessTokenCallMethodOnServerSocket() {
        mServerSocket.startServer((Map<String, String>) any(), (TokenPluginCallback) any());
        verify(mServerSocket).startServer((Map<String, String>) any(), (TokenPluginCallback) any());
    }

    @Test
    public void getAccessTokenNotifiesFailedIfParamsCannotBeDeterminedAndExistsNotNullCallback() {
        TokenPluginCallback pluginCallback = Mockito.mock(TokenPluginCallback.class);

        mServerSocket.startServer(generateRequestParamsMap(), pluginCallback);
        verify(mServerSocket, never()).startServer(null, pluginCallback);
    }

    @Test
    public void getAccessTokenDoesNothingIfParamsCannotBeDeterminedAndCallbackIsNull() {
        verify(mServerSocket, never()).startServer(requestParameters, null);
    }

    @Test
    public void getConnectionParamsReturnsParams() {
        Map<String, String> connParams = generateRequestParamsMap();

        assertTrue(connParams.containsKey(AuthParamKey.REFRESH_TOKEN.name()));
        assertTrue(connParams.containsKey(AuthParamKey.ID_TOKEN.name()));
        assertTrue(connParams.containsKey(AuthParamKey.QR_CODE.name()));
        assertEquals(connParams.get(AuthParamKey.REFRESH_TOKEN.name()), mRefreshToken);
        assertEquals(connParams.get(AuthParamKey.ID_TOKEN.name()), mIdTokenParam);

        String qrParam = connParams.get(AuthParamKey.QR_CODE.name());
        assertTrue(qrParam.contains(mQrId));
        assertTrue(qrParam.contains(mBrowserClient));
    }

    private Map<String, String> generateRequestParamsMap() {
        Map<String, String> requestParameters = new HashMap<>();
        String mUrl = "http://www.randomurl.com";
        requestParameters.put(AuthParamKey.QR_SERVER_URL.name(), mUrl);
        requestParameters.put(AuthParamKey.CERT_PINNER.name(), "");
        requestParameters.put(AuthParamKey.ID_TOKEN.name(), mIdTokenParam);
        requestParameters.put(AuthParamKey.REFRESH_TOKEN.name(), mRefreshToken);
        requestParameters.put(AuthParamKey.QR_CODE.name(), mQrParam);
        return requestParameters;
    }

}
