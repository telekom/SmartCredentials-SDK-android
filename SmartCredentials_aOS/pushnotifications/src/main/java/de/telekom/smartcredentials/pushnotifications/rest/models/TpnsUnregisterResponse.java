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

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;

/**
 * Created by gabriel.blaj@endava.com at 5/19/2020
 */
public class TpnsUnregisterResponse {

    @SerializedName("success")
    private boolean mSuccessState;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("errors")
    private List<TpnsError> mErrors;

    public TpnsUnregisterResponse(boolean success, String message, List<TpnsError> errors) {
        mSuccessState = success;
        mMessage = message;
        mErrors = errors;
    }

    public boolean isSuccessful() {
        return mSuccessState;
    }

    public String getMessage() {
        return mMessage;
    }

    public List<PushNotificationsError> getErrorsList() {
        return new ArrayList<>(mErrors);
    }
}
