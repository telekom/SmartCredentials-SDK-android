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

package de.telekom.smartcredentials.qrlogin.websocket.model;

import com.google.gson.annotations.SerializedName;

public class SuccessAuthInfo {

    @SerializedName("access_token")
    public String mAccessToken;

    @SerializedName("id_token")
    public String mIdToken;

    @SerializedName("scope")
    public String mScope;

    @SerializedName("expires_in")
    public String mExpireTime;

    @SerializedName("token_type")
    public String mTokenType;

    @Override
    public String toString() {
        return "SuccessAuthInfo{" +
                "mAccessToken='" + mAccessToken + '\'' +
                ", mIdToken='" + mIdToken + '\'' +
                ", mScope='" + mScope + '\'' +
                ", mExpireTime='" + mExpireTime + '\'' +
                ", mTokenType='" + mTokenType + '\'' +
                '}';
    }
}
