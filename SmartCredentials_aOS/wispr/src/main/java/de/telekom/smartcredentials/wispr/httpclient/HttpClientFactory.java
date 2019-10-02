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

package de.telekom.smartcredentials.wispr.httpclient;

import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;

import okhttp3.CacheControl;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpClientFactory {

    private final OkHttpClient mHttpClient;

    public HttpClientFactory(long wisprTimeout, CertificatePinner certificatePinner) {
        mHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(wisprTimeout, TimeUnit.SECONDS)
                .readTimeout(wisprTimeout, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                        .header("User-Agent", "wispr")
                        .header("Cache-Control", CacheControl.FORCE_NETWORK.toString())
                        .build())).build();
    }

    public OkHttpClient createHttpClient(SocketFactory socketFactory, boolean followRedirects) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return mHttpClient.newBuilder()
                .addInterceptor(logging)
                .socketFactory(socketFactory)
                .followRedirects(followRedirects)
                .followSslRedirects(followRedirects)
                .build();
    }
}
