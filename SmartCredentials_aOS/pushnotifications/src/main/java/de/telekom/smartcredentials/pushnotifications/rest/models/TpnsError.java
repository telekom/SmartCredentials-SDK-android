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

package de.telekom.smartcredentials.pushnotifications.rest.models;

import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;

/**
 * Created by gabriel.blaj@endava.com at 5/19/2020
 */
public class TpnsError extends PushNotificationsError {

    @SerializedName("field")
    private String mField;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("errorCode")
    private String mErrorCode;

    public TpnsError(String field, String message, String errorCode) {
        mField = field;
        mMessage = message;
        mErrorCode = errorCode;
    }

    public String getField() {
        return mField;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getErrorCode() {
        return mErrorCode;
    }
}
