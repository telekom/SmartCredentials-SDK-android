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

package de.telekom.smartcredentials.networking.request.models;

import de.telekom.smartcredentials.networking.request.models.enums.RequestFailure;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;

public class FailedRequest extends de.telekom.smartcredentials.core.model.request.FailedRequest<RequestFailureLevel> {

    static final String TYPE_MISMATCHED = "Mismatched type! Failure type: %s and details: %s";

    private final RequestFailureLevel mRequestFailureLevel;

    private FailedRequest(RequestFailureLevel requestFailureLevel, Object details, String message) {
        mRequestFailureLevel = requestFailureLevel;
        mMessage = message;
        if (mRequestFailureLevel == RequestFailureLevel.SOCKET_CREATION && !(details instanceof SocketFactoryFailure)
                || (mRequestFailureLevel == RequestFailureLevel.REQUEST && !(details instanceof RequestFailure))) {
            throw new RuntimeException(String.format(TYPE_MISMATCHED, mRequestFailureLevel.name(), details.getClass().getSimpleName()));
        }
        mDetails = details;
    }

    public static FailedRequest newInstance(RequestFailureLevel requestFailureLevel, Object details, String message) {
        return new FailedRequest(requestFailureLevel, details, message);
    }

    public static FailedRequest newInstance(RequestFailureLevel requestFailureLevel, Object details) {
        return newInstance(requestFailureLevel, details, null);
    }

    public RequestFailureLevel getRequestFailureLevel() {
        return mRequestFailureLevel;
    }
}
