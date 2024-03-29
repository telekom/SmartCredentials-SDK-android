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
package de.telekom.identityprovider.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by teodorionut.ganga@endava.com at 23/02/2023
 */
class GetBearerBody(
    @Expose @SerializedName("accessToken") var accessToken: String,
    @Expose @SerializedName("clientId") var clientId: String,
    @Expose @SerializedName("bundleId") var bundleId: String?,
    @Expose @SerializedName("packageName") var packageName: String
)