/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.rest;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Rx3EidService {

    @GET("log-failure")
    Completable logFailure(@Query("errorCode") String errorCode,
                           @Query("jwt") String jwt,
                           @Query("OS") String os,
                           @Query("vendor") String vendor,
                           @Query("model") String model,
                           @Query("sicv") String sicv);

    @GET("error")
    Observable<String> getError(@Query("errorCode") String errorCode);

    @GET("patch/check/android/{version}")
    Observable<Boolean> checkPatchLevel(@Path("version") String version);
}
