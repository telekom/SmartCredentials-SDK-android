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

package de.telekom.smartcredentials.networking.request.generic;

import android.support.annotation.NonNull;

import java.io.IOException;

import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.enums.RequestFailure;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static de.telekom.smartcredentials.networking.request.models.enums.RequestFailure.GENERIC_FAILURE;

public class GenericRequestCallback implements Callback {

    static final String UNEXPECTED_CODE_MESSAGE = "Unexpected code: ";

    private final ServicePluginCallback mServicePluginCallback;

    GenericRequestCallback(ServicePluginCallback servicePluginCallback) {
        mServicePluginCallback = servicePluginCallback;
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull final Response response) {
        mServicePluginCallback.onResponse(response);
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        notifyRequestFailed(GENERIC_FAILURE, UNEXPECTED_CODE_MESSAGE + e.getMessage());
    }

    void notifyRequestFailed(RequestFailure requestFailure, String message) {
        FailedRequest request = FailedRequest.newInstance(RequestFailureLevel.REQUEST, requestFailure, message);
        mServicePluginCallback.onFailed(request);
    }
}
