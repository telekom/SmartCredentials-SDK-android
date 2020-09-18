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

import java.util.Map;

import okhttp3.HttpUrl;

public class UrlBuilder {

    public static String getUrlWithQueryParams(HttpUrl httpUrl, Map<String, String> queryParams) {
        HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
        if (queryParams != null && queryParams.size() > 0) {
            for (String queryParamKey : queryParams.keySet()) {
                urlBuilder.addQueryParameter(queryParamKey, queryParams.get(queryParamKey));
            }
        }

        return urlBuilder.build().toString();
    }
}
