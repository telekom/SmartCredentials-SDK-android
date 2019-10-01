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
import de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationEapParser;
import de.telekom.smartcredentials.wispr.replies.SmartWisprAuthenticationEapReply;
import de.telekom.smartcredentials.wispr.services.WisprService;

public class WisprAuthenticationEapStrategy extends WisprAuthenticationStrategy {

    public WisprAuthenticationEapStrategy(WisprAuthenticationCallback authenticationCallback) {
        super(authenticationCallback);
    }

    @Override
    public String login(WisprService wisprService, String url, String username, String password,
                        String eapMessage, Map<String, String> loginParameters) throws IOException, WisprException {
        WisprAuthenticationEapParser eapParser = new WisprAuthenticationEapParser(mAuthenticationCallback);

        // 1st HTTP POST with EAP message
        String authenticationEapBody = wisprService.login(url, username, eapMessage, "2.0").execute().body();
        SmartWisprAuthenticationEapReply authenticationEapReply = eapParser.parse(authenticationEapBody);

        // 2nd HTTP POST with EAP message
        String authenticationBody = wisprService.login(url, username, authenticationEapReply.getEapMessage(), "2.0").execute().body();
        SmartWisprAuthenticationEapReply authenticationReply = eapParser.parse(authenticationBody);
        return authenticationReply.getLoginUrl();
    }
}
