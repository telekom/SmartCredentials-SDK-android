/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.mobileconnect.responses;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Alex.Graur@endava.com at 9/10/2020
 */
public abstract class DiscoveryExplicitResponse implements Serializable {

    public abstract String getClientId();

    public abstract String getClientSecret();

    public abstract String getServingOperator();

    public abstract String getCountry();

    public abstract String getCurrency();

    public abstract JSONObject getApis();
}
