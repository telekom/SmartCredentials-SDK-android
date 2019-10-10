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

package de.telekom.smartcredentials.networking.actions;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.request.FailedRequest;
import de.telekom.smartcredentials.core.model.request.Method;
import de.telekom.smartcredentials.core.model.request.NetworkConnectionType;
import de.telekom.smartcredentials.networking.itemdatamodel.RequestParamsBuilder;
import de.telekom.smartcredentials.networking.request.generic.GenericHttpHandler;
import de.telekom.smartcredentials.networking.request.generic.GenericService;
import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import okhttp3.CertificatePinner;
import okhttp3.Dns;

@SuppressWarnings("unused")
public class ActionCallService extends SmartCredentialsAction {

    public static final String JSON_MEDIA_TYPE = "application/json";
    public static final String KEY_CONNECTION_TYPE = "connection_type";
    public static final String KEY_END_POINT = "endpoint";
    public static final String KEY_REQUEST_TYPE = "request_type";
    public static final String KEY_HEADERS = "headers";
    public static final String KEY_HEADERS_KEY = "headers_key";
    public static final String KEY_HEADERS_VALUE = "headers_value";
    public static final String KEY_QUERY_PARAMETERS = "query_parameters";
    public static final String KEY_QUERY_PARAMETERS_KEY = "query_parameters_key";
    public static final String KEY_QUERY_PARAMETERS_VALUE = "query_parameters_value";
    public static final String KEY_BODY_PARAMETERS = "body_parameters";

    private ItemEnvelope mItemEnvelope;

    public ActionCallService() {
        // required empty constructor
    }

    public ActionCallService(Context context, String id, String name, JSONObject data) {
        super(id, name, data);
    }

    @Override
    public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {
        try {
            mItemEnvelope = ModelConverter.toItemEnvelope(itemDomainModel);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not convert ItemDomain to ItemEnvelope");
        }

        ServicePluginCallback requestCallback = getServicePluginCallback(callback);
        GenericService service = new GenericService(new CertificatePinner.Builder());
        GenericHttpHandler genericHttpHandler = new GenericHttpHandler(service);

        genericHttpHandler.performRequest(context, generateRequestParams(), requestCallback);

    }

    private ServicePluginCallback getServicePluginCallback(ExecutionCallback callback) {
        return new ServicePluginCallback() {
            @Override
            public void onResponse(Object response) {
                callback.onComplete(new ActionCallServiceResponse(response, "Call Service Request successful"));
            }

            @Override
            public void onFailed(FailedRequest message) {
                callback.onFailed(new ActionCallServiceResponse(message, "Call Service Request failed"));
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Call Service Request failed");
            }
        };
    }

    /**
     * Generates the request parameters by getting the needed data from the item envelope or from the action's data.
     */
    private de.telekom.smartcredentials.core.model.request.RequestParams generateRequestParams() {
        RequestParamsBuilder requestParamsBuilder = new RequestParamsBuilder()
                .setEndpoint(getEndpointFromData())
                .setNetworkConnectionType(getConnectionTypeFromData())
                .setRequestMethod(getRequestMethodFromData())
                .setHeaders(getHeadersFromItem())
                .setQueryParams(getQueryParametersFromItem())
                .setRequestBody(getRequestBodyFromItem())
                .setRequestBodyMediaType(JSON_MEDIA_TYPE)
                .setFollowsRedirects(true)
                .setTimeoutMillis(0)
                .setDns(Dns.SYSTEM)
                .setInterceptorList(new ArrayList<>())
                .setPinsCertificatesMap(new HashMap<>());

        return requestParamsBuilder.build();
    }

    /**
     * Returns the needed query parameters from item's meta data.
     */
    private Map<String, String> getQueryParametersFromItem() {
        Map<String, String> queryParams = new HashMap<>();

        if (mItemEnvelope.getDetails().has(KEY_QUERY_PARAMETERS)) {
            try {
                JSONArray queryParametersArray = mItemEnvelope.getDetails().getJSONArray(KEY_QUERY_PARAMETERS);
                for (int i = 0; i < queryParametersArray.length(); i++) {
                    JSONObject jsonQuery = queryParametersArray.getJSONObject(i);
                    String key = jsonQuery.getString(KEY_QUERY_PARAMETERS_KEY);
                    String value = jsonQuery.getString(KEY_QUERY_PARAMETERS_VALUE);
                    queryParams.put(key, value);
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while parsing the query parameters");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No Query Parameters have been found");
        }

        return queryParams;
    }

    /**
     * Returns the needed headers from item's meta data.
     */
    private Map<String, String> getHeadersFromItem() {
        Map<String, String> headers = new HashMap<>();

        if (mItemEnvelope.getDetails().has(KEY_HEADERS)) {
            try {
                JSONArray headersArray = mItemEnvelope.getDetails().getJSONArray(KEY_HEADERS);
                if (headersArray.length() > 0) {
                    for (int i = 0; i < headersArray.length(); i++) {
                        JSONObject jsonHeader = headersArray.getJSONObject(i);
                        String key = jsonHeader.getString(KEY_HEADERS_KEY);
                        String value = jsonHeader.getString(KEY_HEADERS_VALUE);
                        headers.put(key, value);
                    }
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while parsing the headers");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No Headers have been found");
        }

        return headers;
    }

    /**
     * Returns the needed request body from item's meta data.
     */
    private String getRequestBodyFromItem() {
        String requestBody = "";
        if (mItemEnvelope.getDetails().has(KEY_BODY_PARAMETERS)) {
            try {
                requestBody = mItemEnvelope.getDetails().getString(KEY_BODY_PARAMETERS);
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while parsing the body parameters");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No Body Parameters have been found");
        }
        return requestBody;
    }

    /**
     * Returns the request method from action's data.
     */
    private Method getRequestMethodFromData() {
        Method method = null;
        String methodString;
        if (getData().has(KEY_REQUEST_TYPE)) {
            try {
                methodString = getData().getString(KEY_REQUEST_TYPE);

                if (methodString.equals(Method.ADD.name())) {
                    method = Method.ADD;
                } else if (methodString.equals(Method.DELETE.name())) {
                    method = Method.DELETE;
                } else if (methodString.equals(Method.GET.name())) {
                    method = Method.GET;
                } else if (methodString.equals(Method.UPDATE.name())) {
                    method = Method.UPDATE;
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while getting the request type");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No Request Method has been found");
        }
        return method;
    }

    /**
     * Returns the network connection type from action's data.
     */
    private NetworkConnectionType getConnectionTypeFromData() {
        NetworkConnectionType connectionType = null;
        String connectionTypeString;
        if (getData().has(KEY_CONNECTION_TYPE)) {
            try {
                connectionTypeString = getData().getString(KEY_CONNECTION_TYPE);

                if (connectionTypeString.equals(NetworkConnectionType.MOBILE.name())) {
                    connectionType = NetworkConnectionType.MOBILE;
                } else if (connectionTypeString.equals(NetworkConnectionType.WIFI.name())) {
                    connectionType = NetworkConnectionType.WIFI;
                } else {
                    connectionType = NetworkConnectionType.DEFAULT;
                }
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while getting the connection type");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No Connection Type has been found");
        }
        return connectionType;
    }

    /**
     * Returns the needed end point from action's data.
     */
    private String getEndpointFromData() {
        String endPoint = null;
        if (getData().has(KEY_END_POINT)) {
            try {
                endPoint = getData().getString(KEY_END_POINT);
            } catch (JSONException e) {
                ApiLoggerResolver.logError(getClass().getSimpleName(), "Error while getting the end point");
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "No End Point has been found");
        }
        return endPoint;
    }

}