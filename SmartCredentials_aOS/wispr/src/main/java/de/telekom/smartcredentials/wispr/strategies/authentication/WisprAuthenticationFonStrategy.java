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

package de.telekom.smartcredentials.wispr.strategies.authentication;

import java.io.IOException;
import java.util.Map;

import de.telekom.smartcredentials.core.wispr.callbacks.WisprAuthenticationCallback;
import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.utils.WisprUriHandler;
import de.telekom.smartcredentials.wispr.services.WisprService;

public class WisprAuthenticationFonStrategy extends WisprAuthenticationStrategy {

    public WisprAuthenticationFonStrategy(WisprAuthenticationCallback authenticationCallback) {
        super(authenticationCallback);
    }

    @Override
    public String login(WisprService wisprService, String url, String username, String password,
                        String eapMessage, Map<String, String> loginParameters) throws IOException, WisprException {
        String newLoginUrl = WisprUriHandler.replaceUriParameter(WisprUriHandler.replaceAmpersandParameter(url), "res", "smartclient");
        return wisprService.login(newLoginUrl, username, password, loginParameters).execute().body();
    }
}
