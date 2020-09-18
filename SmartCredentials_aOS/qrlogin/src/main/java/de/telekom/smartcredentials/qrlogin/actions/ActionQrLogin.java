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

package de.telekom.smartcredentials.qrlogin.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;
import de.telekom.smartcredentials.core.qrlogin.TokenPluginError;
import de.telekom.smartcredentials.qrlogin.AuthParamKey;
import de.telekom.smartcredentials.qrlogin.websocket.ServerSocket;
import okhttp3.CertificatePinner;
import okhttp3.Request;

@SuppressWarnings("unused")
public class ActionQrLogin extends SmartCredentialsAction {

    private ItemEnvelope mItemEnvelope;

    public ActionQrLogin() {
        // required empty constructor
    }

    public ActionQrLogin(String id, String name, JSONObject data) {
        super(id, name, data);
    }

    @Override
    public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {
        try {
            mItemEnvelope = ModelConverter.toItemEnvelope(itemDomainModel);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not convert ItemDomain to ItemEnvelope");
        }
        ServerSocket serverSocket = new ServerSocket(new Request.Builder(), new CertificatePinner.Builder());
        try {
            serverSocket.startServer(generateRequestParameters(), getServicePluginCallback(callback));
        } catch (IllegalArgumentException e) {
            callback.onFailed(TokenPluginError.INVALID_URL);
        }
    }

    private TokenPluginCallback getServicePluginCallback(final ExecutionCallback callback) {
        return new TokenPluginCallback<TokenResponse, Object>() {
            @Override
            public void onSuccess(TokenResponse result) {
                callback.onComplete(new ActionQrLoginResponse(result, "Qr Login Request successful"));
            }

            @Override
            public void onFailed(Object message) {
                callback.onFailed(new ActionQrLoginResponse(message, "Qr Login Request failed"));
            }
        };
    }

    private String getEndpointUrlFromData() {
        String endpointUrl = null;

        if (getData().has(AuthParamKey.QR_SERVER_URL.name())) {
            try {
                endpointUrl = getData().getString(AuthParamKey.QR_SERVER_URL.name());
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while " +
                        "parsing the QR URL parameter");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No QR URL " +
                    "parameter have been found");
        }
        return endpointUrl;
    }

    private String getQrCodeFromData() {
        String qrCode = null;

        if (mItemEnvelope.getDetails().has(AuthParamKey.QR_CODE.name())) {
            try {
                qrCode = mItemEnvelope.getDetails().getString(AuthParamKey.QR_CODE.name());
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while " +
                        "parsing the QR code parameter");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No QR code query " +
                    "parameter have been found");
        }
        return qrCode;
    }

    private String getIdTokenFromData() {
        String idToken = null;

        if (mItemEnvelope.getDetails().has(AuthParamKey.ID_TOKEN.name())) {
            try {
                idToken = mItemEnvelope.getDetails().getString(AuthParamKey.ID_TOKEN.name());
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while " +
                        "parsing the id token parameter");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No id token " +
                    "parameter have been found");
        }
        return idToken;
    }

    private String getRefreshTokenFromData() {
        String refreshToken = null;

        if (mItemEnvelope.getDetails().has(AuthParamKey.REFRESH_TOKEN.name())) {
            try {
                refreshToken = mItemEnvelope.getDetails().getString(AuthParamKey.REFRESH_TOKEN.name());
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while " +
                        "parsing the refresh token parameter");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No refresh token " +
                    "parameter have been found");
        }
        return refreshToken;
    }

    private boolean hasCertificatePinner() {
        return mItemEnvelope.getDetails().has(AuthParamKey.CERT_PINNER.name());
    }

    private String getCertificatePinnerFromData() {
        String certificatePinner = null;

        if (hasCertificatePinner()) {
            try {
                certificatePinner = mItemEnvelope.getDetails().getString(AuthParamKey.CERT_PINNER.name());
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while " +
                        "parsing the certificate pinner parameter");
            }
        }
        return certificatePinner;
    }

    private Map<String, String> generateRequestParameters() {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put(AuthParamKey.QR_SERVER_URL.name(), getEndpointUrlFromData());
        if (hasCertificatePinner()) {
            requestParameters.put(AuthParamKey.CERT_PINNER.name(), getCertificatePinnerFromData());
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No certificate pinner " +
                    "parameter have been found");
        }
        requestParameters.put(AuthParamKey.ID_TOKEN.name(), getIdTokenFromData());
        requestParameters.put(AuthParamKey.REFRESH_TOKEN.name(), getRefreshTokenFromData());
        requestParameters.put(AuthParamKey.QR_CODE.name(), getQrCodeFromData());
        return requestParameters;
    }
}
