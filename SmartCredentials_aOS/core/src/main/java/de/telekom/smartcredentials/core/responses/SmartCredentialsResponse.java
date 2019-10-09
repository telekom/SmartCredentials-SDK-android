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

package de.telekom.smartcredentials.core.responses;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Objects;

public class SmartCredentialsResponse<V> implements SmartCredentialsApiResponse<V> {

    static final String GET_DATA_RESPONSE = "Response contains error, not data; Call SmartCredentialsResponse#isSuccessful() first.";
    static final String GET_ERROR_RESPONSE = "Error is null; Call SmartCredentialsResponse#isSuccessful() first.";

    @Nullable
    private final Throwable mError;

    @Nullable
    private final V mData;

    public SmartCredentialsResponse() {
        mData = null;
        mError = null;
    }

    /**
     * In case of successful API request, pass the received mData here.
     *
     * @param data received mData from successful API Call.
     */
    public SmartCredentialsResponse(@Nullable final V data) {
        mData = data;
        mError = null;
    }

    /**
     * In case of failed API request, pass mError.
     *
     * @param error The cause of the failed API Call.
     */
    public SmartCredentialsResponse(@NonNull final Throwable error) {
        Objects.requireNonNull(error);

        mError = error;
        mData = null;
    }

    @Override
    public boolean isSuccessful() {
        return mError == null;
    }

    @Nullable
    @Override
    public V getData() {
        if (mError != null) {
            throw new IllegalStateException(GET_DATA_RESPONSE);
        }
        return mData;
    }

    @Override
    @NonNull
    public Throwable getError() {
        if (mError == null) {
            throw new IllegalStateException(GET_ERROR_RESPONSE);
        }
        return mError;
    }
}