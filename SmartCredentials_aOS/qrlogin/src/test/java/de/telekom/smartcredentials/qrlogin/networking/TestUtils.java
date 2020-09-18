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

package de.telekom.smartcredentials.qrlogin.networking;

import java.util.HashMap;
import java.util.Map;

import de.telekom.smartcredentials.core.networking.AuthParamKey;
import de.telekom.smartcredentials.qrlogin.websocket.model.WebSocketMessage;

@SuppressWarnings("SpellCheckingInspection")
class TestUtils {

    private static final String ACCESS_TOKEN = "acces_token";
    private static final String FAIL_TEXT = "failure_message";

    static final String CONNECTION_SUCCESS_RESPONSE =
            "{\"event\":\"" + "Connection#Success"
                    + "\",\"data\":{\"_id\":\"id\"}}";
    static final String AUTH_SUCCESS_RESPONSE =
            "{\"event\":\"" + "Success#QRCodeScan"
                    + "\",\"data\":{\"access_token\":\"" + ACCESS_TOKEN + "\","
                    + "\"id_token\":\"your_id_token\", "
                    + "\"scope\":\"openid\", "
                    + "\"expires_in\":86400,"
                    + "\"token_type\":\"Bearer\"}}";
    static final String AUTH_FAIL_RESPONSE =
            "{\"event\":\"" + "Error#QRCodeScan"
                    + "\",\"data\":{\"error\":{\"status\":403, \"message\":\"" + FAIL_TEXT + "\"}}}";
    static final String OTHER_RESPONSE =
            "{\"event\":\"" + "SomeResponse"
                    + "\",\"data\":{\"error\":{\"status\":403, \"message\":\"" + FAIL_TEXT + "\"}}}";
    private static final String QR_ID = "2138127398";
    private static final String BROWSER_CLIENT = "client";
    private static final String WS_EMIT_EVENT_QR_SCANNED = "Scanning#QRCode";
    private static final String BARCODE_JSON = "{\"browserClient\":\"" + BROWSER_CLIENT
            + "\",\"qr_uuid\":\"" + QR_ID + "\"}";
    private static final String ID_TOKEN =
            "id_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    static WebSocketMessage createWebSocketMessage() {
        return new WebSocketMessage(
                WS_EMIT_EVENT_QR_SCANNED,
                QR_ID,
                BROWSER_CLIENT,
                ID_TOKEN,
                REFRESH_TOKEN);
    }

    static Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put(AuthParamKey.REFRESH_TOKEN.name(), REFRESH_TOKEN);
        params.put(AuthParamKey.ID_TOKEN.name(), ID_TOKEN);
        params.put(AuthParamKey.QR_CODE.name(), BARCODE_JSON);

        return params;
    }
}
