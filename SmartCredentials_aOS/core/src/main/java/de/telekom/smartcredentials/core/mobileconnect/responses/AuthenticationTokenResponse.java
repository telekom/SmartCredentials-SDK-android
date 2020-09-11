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

import java.io.Serializable;

/**
 * Created by Alex.Graur@endava.com at 9/11/2020
 */
public abstract class AuthenticationTokenResponse implements Serializable {

    public abstract String getTokenType();

    public abstract String getIdToken();

    public abstract String getAccessToken();

    public abstract String getTokenExpirationTime();

    public abstract String getCorrelationId();
}
