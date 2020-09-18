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

package de.telekom.smartcredentials.qrlogin.qrlogin.request.models;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.telekom.smartcredentials.qrlogin.Method;
import de.telekom.smartcredentials.qrlogin.NetworkConnectionType;
import de.telekom.smartcredentials.qrlogin.RequestParams;
import de.telekom.smartcredentials.qrlogin.request.models.SmartRequestParams;
import de.telekom.smartcredentials.qrlogin.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.qrlogin.request.models.enums.HttpCrud;
import okhttp3.Dns;
import okhttp3.Interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class SmartRequestParamsTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void builderCreatesNewInstance() {
        String endpoint = "http://test.com";
        Map<String, String> headers = new HashMap<>();
        headers.put("header1", "headerValue");
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "test");
        String requestBody = "{\"id\": 4}";
        String requestBodyMediaType = "application/json";
        Method method = Method.UPDATE;
        NetworkConnectionType networkConnectionType = NetworkConnectionType.MOBILE;
        Map<String, List<String>> certPins = new HashMap<>();
        certPins.put(endpoint, Collections.singletonList("certTest"));
        boolean followsRedirect = false;
        long timeout = 1000;
        List<Interceptor> interceptorList = new ArrayList<>();
        Dns dns = Dns.SYSTEM;
        RequestParams reqParams =
                new RequestParams.Builder()
                .setEndpoint(endpoint)
                .setHeaders(headers)
                .setQueryParams(queryParams)
                .setRequestBody(requestBody)
                .setRequestBodyMediaType(requestBodyMediaType)
                .setMethod(method)
                .setNetworkConnectionType(networkConnectionType)
                .setPinsCertificatesMap(certPins)
                .setFollowsRedirects(followsRedirect)
                .setTimeoutMillis(timeout)
                .setInterceptorList(interceptorList)
                .setDns(dns)
                .build();

        when(TextUtils.isEmpty(requestBody)).thenReturn(false);

        SmartRequestParams smartRequestParams =
                new SmartRequestParams(reqParams);

        assertEquals(endpoint, smartRequestParams.getEndpoint());
        assertEquals(headers, smartRequestParams.getHeaders());
        assertEquals(queryParams, smartRequestParams.getQueryParams());
        assertNotNull(smartRequestParams.getRequestBody());
        assertEquals(requestBodyMediaType, smartRequestParams.getBodyType());
        assertEquals(HttpCrud.map(method).name(), smartRequestParams.getHttpMethod());
        assertEquals(ConnectionType.map(networkConnectionType), smartRequestParams.getConnectionType());
        assertEquals(certPins, smartRequestParams.getPinsCertificatesMap());
        assertEquals(followsRedirect, smartRequestParams.followsRedirects());
        assertEquals(timeout, smartRequestParams.getTimeoutMillis());
        assertEquals(interceptorList, smartRequestParams.getInterceptorList());
        assertEquals(dns, smartRequestParams.getDns());
    }

}