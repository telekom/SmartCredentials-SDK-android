/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.pushnotifications.rest;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.pushnotifications.rest.http.HttpClientFactory;
import de.telekom.smartcredentials.pushnotifications.rest.service.TpnsService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gabriel.blaj@endava.com at 5/18/2020
 */
public class RetrofitClient {

    private final static String TEST_URL = "https://tpns-preprod.molutions.de/";
    private final static String PRODUCTION_URL = "https://tpns.molutions.de/";

    private HttpClientFactory mHttpClientFactory;

    public RetrofitClient(HttpClientFactory httpClientFactory) {
        mHttpClientFactory = httpClientFactory;
    }

    public TpnsService createTpnsService(String url) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(mHttpClientFactory.createHttpClient())
                .baseUrl(url)
                .build().create(TpnsService.class);
    }

    public String getTpnsUrl(boolean isInProduction) {
        if(isInProduction) {
            return PRODUCTION_URL;
        } else {
            return TEST_URL;
        }
    }
}
