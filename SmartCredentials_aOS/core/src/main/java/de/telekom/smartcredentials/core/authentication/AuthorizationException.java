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

package de.telekom.smartcredentials.core.authentication;

import android.net.Uri;

/**
 * Created by Lucian Iacob on March 05, 2019.
 */
@SuppressWarnings("unused")
public class AuthorizationException {

    private final int mType;
    private final int mCode;
    private final String mError;
    private final String mErrorDescription;
    private final Uri mErrorUri;

    public AuthorizationException(int type, int code, String error, String errorDescription, Uri errorUri) {
        mType = type;
        mCode = code;
        mError = error;
        mErrorDescription = errorDescription;
        mErrorUri = errorUri;
    }

    public String getError() {
        return mError;
    }

    public String getErrorDescription() {
        return mErrorDescription;
    }

    public Uri getErrorUri() {
        return mErrorUri;
    }

    public int getCode() {
        return mCode;
    }

    public int getType() {
        return mType;
    }
}
