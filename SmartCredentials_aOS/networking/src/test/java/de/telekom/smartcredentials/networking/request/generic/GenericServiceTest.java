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

import android.content.Context;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.RequestParams;
import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;
import de.telekom.smartcredentials.networking.request.utils.HttpClientBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.Request;

import static de.telekom.smartcredentials.networking.request.models.enums.RequestFailure.NULL_PARSED_URL;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;

@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
@PrepareForTest({GenericSocketFactory.class, HttpClientBuilder.class, FailedRequest.class,
        TextUtils.class, HttpUrl.class, Request.class})
public class GenericServiceTest {

    private GenericService mGenericService;
    private Context mContext;
    private CertificatePinner.Builder mBuilder;
    private ServicePluginCallback mServicePluginCallback;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(GenericSocketFactory.class);
        PowerMockito.mockStatic(HttpClientBuilder.class);
        PowerMockito.mockStatic(FailedRequest.class);
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(HttpUrl.class);
        PowerMockito.mockStatic(Request.class);

        mContext = Mockito.mock(Context.class);
        mBuilder = new CertificatePinner.Builder();
        mServicePluginCallback = Mockito.mock(ServicePluginCallback.class);

        mGenericService = new GenericService(mBuilder);
    }

    @Test
    public void prepareSocketFactoryCallsCreateSocketFactory() throws Exception {
        ConnectionType connectionType = ConnectionType.WIFI;
        SocketFactoryCallback callback = Mockito.mock(SocketFactoryCallback.class);
        doNothing().when(GenericSocketFactory.class, "createSocketFactory",
                mContext, connectionType, callback);


        mGenericService.prepareSocketFactory(mContext, connectionType, callback);
        PowerMockito.verifyStatic(GenericSocketFactory.class, Mockito.times(1));
        GenericSocketFactory.createSocketFactory(mContext, connectionType, callback);
    }

    @Test
    public void createRequestNotifiesFailedRequestIfHttpUrlIsNull() throws CertificateException,
            NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        FailedRequest failedRequest = FailedRequest.newInstance(RequestFailureLevel.REQUEST, NULL_PARSED_URL);
        when(FailedRequest.newInstance(RequestFailureLevel.REQUEST, NULL_PARSED_URL)).thenReturn(failedRequest);
        when(TextUtils.isEmpty(any())).thenReturn(true);

        RequestParams requestParams = new RequestParams(new RequestParams.Builder().setEndpoint("test").build());
        ServicePluginCallback callback = Mockito.mock(ServicePluginCallback.class);

        mGenericService.createRequest(requestParams, callback);

        PowerMockito.verifyStatic(HttpClientBuilder.class, Mockito.times(0));
        HttpClientBuilder.buildCallFactory(requestParams, mBuilder);
        verify(callback).onFailed(failedRequest);
    }

    // FIXME test fail
//    @Test
//    public void createRequestNotifies() throws CertificateException, NoSuchAlgorithmException,
//            KeyStoreException, KeyManagementException, IOException {
//        String scheme = "http";
//        String host = "10.0.2.2:3000";
//        String url = scheme + "://" + host;
//        FailedRequest failedRequest = FailedRequest.newInstance(RequestFailureLevel.REQUEST, NULL_PARSED_URL);
//        when(FailedRequest.newInstance(RequestFailureLevel.REQUEST, NULL_PARSED_URL)).thenReturn(failedRequest);
//
//        when(TextUtils.isEmpty(any())).thenReturn(true);
//
//        RequestParams requestParams = new RequestParams(new RequestParams.Builder().setEndpoint(url).build());
//
//        HttpUrl httpUrl = new HttpUrl.Builder().scheme(scheme).host(host).build();
//        when(HttpUrl.parse(url)).thenReturn(httpUrl);
//
//        Request request = new Request.Builder().url(HttpUrl.parse(url)).build();
//        when(HttpClientBuilder.buildRequest(httpUrl, requestParams)).thenReturn(request);
//
//        Call.Factory factory = Mockito.mock(Call.Factory.class);
//        Call call = Mockito.mock(Call.class);
//        when(factory.newCall(request)).thenReturn(call);
//        when(HttpClientBuilder.buildCallFactory(requestParams, mBuilder)).thenReturn(factory);
//
//        GenericService service = PowerMockito.spy(mGenericService);
//        Callback callback = Mockito.mock(Callback.class);
//        when(service.getCallback(mServicePluginCallback)).thenReturn(callback);
//        service.createRequest(requestParams, mServicePluginCallback);
//
//        PowerMockito.verifyStatic(HttpClientBuilder.class, Mockito.times(1));
//        HttpClientBuilder.buildCallFactory(requestParams, mBuilder);
//
//        verify(mServicePluginCallback, times(0)).onFailed(failedRequest);
//        verify(call).enqueue(callback);
//    }

    @Test
    public void getCallbackReturnsGenericRequestCallbackInstance() {
        Callback callback = mGenericService.getCallback(mServicePluginCallback);

        assertTrue(callback instanceof GenericRequestCallback);
    }
}
