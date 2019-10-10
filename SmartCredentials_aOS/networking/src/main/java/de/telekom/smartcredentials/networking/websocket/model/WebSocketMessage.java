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

package de.telekom.smartcredentials.networking.websocket.model;

import com.google.gson.annotations.SerializedName;

public class WebSocketMessage {

    @SerializedName("event")
    public final String mEvent;

    @SerializedName("data")
    public final Object mData;

    public WebSocketMessage(String event,
            String qr_uuid,
            String browserClient,
            String idToken,
            String refreshToken) {
        this.mEvent = event;
        this.mData = new QrSocketData(qr_uuid, browserClient, idToken, refreshToken);
    }

    @Override
    public String toString() {
        return "WebSocketMessage{" +
                "mEvent='" + mEvent + '\'' +
                ", mData=" + mData.toString() +
                '}';
    }

}
