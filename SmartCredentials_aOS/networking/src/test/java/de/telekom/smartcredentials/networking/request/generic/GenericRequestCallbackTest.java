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

package de.telekom.smartcredentials.networking.request.generic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.enums.RequestFailure;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import okhttp3.Call;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSource;

import static de.telekom.smartcredentials.networking.request.generic.GenericRequestCallback.UNEXPECTED_CODE_MESSAGE;
import static de.telekom.smartcredentials.networking.request.models.enums.RequestFailure.GENERIC_FAILURE;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({FailedRequest.class})
public class GenericRequestCallbackTest {

    private GenericRequestCallback mGenericRequestCallback;
    private ServicePluginCallback mServicePluginCallback;
    private Response.Builder mResponseBuilder;
    private Call mCall;

    @Before
    public void setUp() {
        mServicePluginCallback = Mockito.mock(ServicePluginCallback.class);
        mGenericRequestCallback = new GenericRequestCallback(mServicePluginCallback);
        mCall = Mockito.mock(Call.class);
        mResponseBuilder = new Response.Builder()
                .request(new Request.Builder().url("http://test.com").build())
                .protocol(Protocol.HTTP_1_1);
    }

    @Test
    public void onResponseNotifiesRequestFailedIfResponseIsUnsuccessful() {
        int code = 400;
        String message = "BAD REQUEST";
        ResponseBody responseBody = Mockito.mock(ResponseBody.class);
        when(responseBody.source()).thenReturn(Mockito.mock(BufferedSource.class));
        Response response = mResponseBuilder
                .code(code)
                .message(message)
                .body(responseBody)
                .build();

        mGenericRequestCallback.onResponse(mCall, response);

        verify(mServicePluginCallback).onResponse(response);
    }

    @Test
    public void onResponseNotifiesRequestSucceeded() {
        GenericRequestCallback genericRequestCallback = PowerMockito.spy(mGenericRequestCallback);
        BufferedSource bufferedSource = Mockito.mock(BufferedSource.class);
        ResponseBody responseBody = new RealResponseBody("application/json", 0L, bufferedSource);
        String message = "Success";
        int code = 200;
        Response response = mResponseBuilder
                .code(code)
                .message(message)
                .body(responseBody)
                .build();

        genericRequestCallback.onResponse(mCall, response);

        verify(mServicePluginCallback).onResponse(response);
    }

    @Test
    public void onFailureNotifiesRequestFailed() {
        GenericRequestCallback genericRequestCallback = PowerMockito.spy(mGenericRequestCallback);
        String exceptionMessage = "test";
        IOException ioException = new IOException(exceptionMessage);
        genericRequestCallback.onFailure(mCall, ioException);

        verify(genericRequestCallback).notifyRequestFailed(GENERIC_FAILURE,
                UNEXPECTED_CODE_MESSAGE + exceptionMessage);
    }

    @Test
    public void notifyRequestFailed() {
        PowerMockito.mockStatic(FailedRequest.class);
        FailedRequest request = Mockito.mock(FailedRequest.class);
        RequestFailure requestFailure = RequestFailure.GENERIC_FAILURE;
        String message = "test";
        when(FailedRequest.newInstance(RequestFailureLevel.REQUEST,
                requestFailure, message)).thenReturn(request);

        mGenericRequestCallback.notifyRequestFailed(requestFailure, message);

        verify(mServicePluginCallback).onFailed(request);
    }
}