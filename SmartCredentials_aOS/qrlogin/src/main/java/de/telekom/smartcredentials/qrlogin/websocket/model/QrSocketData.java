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

public class QrSocketData {

    @SerializedName("qr_uuid")
    public final String mQrID;

    @SerializedName("browserClient")
    public final String mBrowserClient;

    @SerializedName("id_token")
    public final String mIdToken;

    @SerializedName("refresh_token")
    public final String mRefreshToken;

    public QrSocketData(String qr_uuid,
            String browserClient,
            String idToken,
            String refreshToken) {
        this.mQrID = qr_uuid;
        this.mBrowserClient = browserClient;
        this.mIdToken = idToken;
        this.mRefreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "QrSocketData{" +
                "mQrID='" + mQrID + '\'' +
                ", mBrowserClient='" + mBrowserClient + '\'' +
                ", mIdToken='" + mIdToken + '\'' +
                ", mRefreshToken='" + mRefreshToken + '\'' +
                '}';
    }
}
