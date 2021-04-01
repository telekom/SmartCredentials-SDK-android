/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.rest;

import de.telekom.smartcredentials.core.eid.EidConfiguration;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private final EidConfiguration mEidConfiguration;

    public RetrofitClient(EidConfiguration configuration) {
        mEidConfiguration = configuration;
    }

    public Rx3EidService getRx3EidService(boolean isProduction) {
        return getRetrofitClientBuilder(isProduction)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build().create(Rx3EidService.class);
    }

    public Rx2EidService getRx2EidService(boolean isProduction) {
        return getRetrofitClientBuilder(isProduction)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Rx2EidService.class);
    }

    private Retrofit.Builder getRetrofitClientBuilder(boolean isProduction) {
        String baseUrl = isProduction ? mEidConfiguration.getProductionUrl() : mEidConfiguration.getTestUrl();
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create());
    }
}
