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

package de.telekom.smartcredentials.networking.request.models.enums;

import de.telekom.smartcredentials.core.model.request.Method;

public enum HttpCrud {

    POST("ADD"),
    PUT("UPDATE"),
    GET("GET"),
    DELETE("DELETE");

    private final String mDesc;

    HttpCrud(String desc) {
        mDesc = desc;
    }

    public static HttpCrud map(Method method) {
        switch (method) {
            case ADD:
                return POST;
            case GET:
                return GET;
            case DELETE:
                return DELETE;
            case UPDATE:
                return PUT;
        }
        return HttpCrud.GET;
    }

    public String getMethod() {
        return mDesc;
    }
}
