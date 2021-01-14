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

package de.telekom.smartcredentials.core.api;

import android.content.Context;
import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.model.request.RequestParams;
import de.telekom.smartcredentials.core.networking.ServerSocket;
import de.telekom.smartcredentials.core.networking.ServiceCallback;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 08, 2018.
 */
public interface NetworkingApi {

    /**
     * Method used to scan a QR code for OTP, parse the response to extract the OTP item and save
     * it encrypted in the sensitive repository
     *
     * @param context         to use for the checking the storage permission and internet connectivity
     * @param requestParams   contains the information needed for creating the request
     * @param serviceCallback {@link ServiceCallback} for retrieving success or failure events
     * @return {@link SmartCredentialsApiResponse} stating if request creating was successful
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Void> callService(@NonNull Context context, @NonNull RequestParams requestParams, @NonNull ServiceCallback serviceCallback);

    /**
     * Method used to fetch a {@link ServerSocket} in order to start a server socket, communicate a
     * message and wait for the server's response if the action performed was successful or not. The
     * communication with the server is made using a web socket listener.
     *
     * @return an instance of {@link ServerSocket} or a throwable otherwise.
     */
    @SuppressWarnings("unused")
    ServerSocket getServerSocket();
}
