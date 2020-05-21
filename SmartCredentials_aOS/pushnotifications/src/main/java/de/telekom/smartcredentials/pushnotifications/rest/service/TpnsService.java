/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.pushnotifications.rest.service;

import com.google.gson.JsonObject;

import de.telekom.smartcredentials.pushnotifications.rest.models.TpnsRegisterResponse;
import de.telekom.smartcredentials.pushnotifications.rest.models.TpnsUnregisterResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by gabriel.blaj@endava.com at 5/18/2020
 */
public interface TpnsService {

    @POST("/TPNS/api/device/register")
    Observable<TpnsRegisterResponse> register(@Body JsonObject requestBody);

    @PUT("/TPNS/api/application/{application_key}/device/{device_id}/unregister")
    Observable<TpnsUnregisterResponse> unregister(@Path("application_key") String applicationKey,
                                                  @Path("device_id") String deviceId);
}
