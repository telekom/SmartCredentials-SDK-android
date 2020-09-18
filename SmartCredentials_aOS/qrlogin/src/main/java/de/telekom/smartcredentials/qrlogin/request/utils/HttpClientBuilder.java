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

package de.telekom.smartcredentials.qrlogin.request.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import de.telekom.smartcredentials.qrlogin.AuthParamKey;
import de.telekom.smartcredentials.qrlogin.request.models.SmartRequestParams;
import de.telekom.smartcredentials.qrlogin.request.models.enums.HttpCrud;
import okhttp3.Call;
import okhttp3.CertificatePinner;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static de.telekom.smartcredentials.qrlogin.request.utils.SSLSocketProvider.getSslSocketFactory;
import static de.telekom.smartcredentials.qrlogin.request.utils.TrustManagerProvider.getTrustManager;
import static de.telekom.smartcredentials.qrlogin.request.utils.UrlBuilder.getUrlWithQueryParams;

public class HttpClientBuilder {

    public static OkHttpClient.Builder getBuilder(String requestUrl, Map<String, String> params,
                                                  CertificatePinner.Builder certificatePinnerBuilder) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (params.containsKey(AuthParamKey.CERT_PINNER.name())) {
            Map<String, List<String>> pins = new HashMap<>();
            // TODO: 6/27/2018 modify parameter that must be set
            pins.put(requestUrl, Collections.singletonList(params.get(AuthParamKey.CERT_PINNER.name())));
            CertificatePinner certificatePinner = getCertificatePinner(pins, certificatePinnerBuilder);
            builder.certificatePinner(certificatePinner);
        }

        return builder;
    }

    public static Call.Factory buildCallFactory(SmartRequestParams smartRequestParams, CertificatePinner.Builder certificatePinnerBuilder) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .socketFactory(smartRequestParams.getSocketFactory())
                .followRedirects(smartRequestParams.followsRedirects())
                .followSslRedirects(smartRequestParams.followsRedirects());

        if (smartRequestParams.hasHeaders()) {
            builder.addInterceptor(chain -> {
                Request.Builder requestBuilder = chain.request().newBuilder();
                for (String headerKey : smartRequestParams.getHeadersKeys()) {
                    requestBuilder.header(headerKey, smartRequestParams.getHeaderValue(headerKey));
                }
                return chain.proceed(requestBuilder.build());
            });
        }
        setTimeout(smartRequestParams.getTimeoutMillis(), smartRequestParams.getHttpMethod(), builder);
        setInterceptors(smartRequestParams, builder);

        if (smartRequestParams.getDns() != null) {
            builder.dns((Dns) smartRequestParams.getDns());
        }

        if (smartRequestParams.hasCertificatesPins()) {
            builder.certificatePinner(getCertificatePinner(smartRequestParams.getPinsCertificatesMap(), certificatePinnerBuilder));
        }

        InputStreamReader reader = new InputStreamReader(smartRequestParams.getTrustCertificate());
        if (reader.ready()) {
            InputStream certificateInputStream = smartRequestParams.getTrustCertificate();
            X509TrustManager x509TrustManager = getTrustManager(certificateInputStream);

            builder.sslSocketFactory(getSslSocketFactory(x509TrustManager), x509TrustManager);
        }

        builder.hostnameVerifier(smartRequestParams.getHostnameVerifier());

        return builder.build();
    }

    public static Request buildRequest(HttpUrl httpUrl, SmartRequestParams smartRequestParams) {
        return new Request.Builder()
                .url(getUrlWithQueryParams(httpUrl, smartRequestParams.getQueryParams()))
                .method(smartRequestParams.getHttpMethod(), smartRequestParams.getRequestBody())
                .build();
    }

    private static CertificatePinner getCertificatePinner(Map<String, List<String>> pinsCertificatesMap, CertificatePinner.Builder certificatePinnerBuilder) {
        for (String key : pinsCertificatesMap.keySet()) {
            for (String pin : pinsCertificatesMap.get(key)) {
                certificatePinnerBuilder.add(key, pin);
            }
        }
        return certificatePinnerBuilder.build();
    }

    private static void setTimeout(long timeout, String httpMethod, OkHttpClient.Builder builder) {
        if (timeout > 0) {
            builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
            if (Objects.equals(httpMethod, HttpCrud.GET.getMethod())) {
                builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
            } else if (Objects.equals(httpMethod, HttpCrud.POST.getMethod()) || Objects.equals(httpMethod, HttpCrud.PUT.getMethod())) {
                builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
            }
        }
    }

    private static void setInterceptors(SmartRequestParams smartRequestParams, OkHttpClient.Builder builder) {
        if (!smartRequestParams.hasInterceptors()) {
            return;
        }
        for (Object interceptor : smartRequestParams.getInterceptorList()) {
            builder.addInterceptor((Interceptor) interceptor);
        }
    }
}
