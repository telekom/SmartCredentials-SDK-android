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
import de.telekom.smartcredentials.wispr.replies.WisprPollReply;
import de.telekom.smartcredentials.wispr.parsers.WisprPollParser;
import de.telekom.smartcredentials.wispr.services.WisprService;

public class WisprAuthenticationPollingStrategy extends WisprAuthenticationStrategy {

    public WisprAuthenticationPollingStrategy(WisprAuthenticationCallback authenticationCallback) {
        super(authenticationCallback);
    }

    @Override
    public String login(WisprService wisprService, String url, String username, String password,
                        String eapMessage, Map<String, String> loginParameters) throws IOException, WisprException, InterruptedException {
        WisprPollParser pollParser = new WisprPollParser();

        // HTTP POST with credentials
        String firstPollResponse = wisprService.login(url, username, password, loginParameters)
                .execute().body();
        WisprPollReply firstPollReply = pollParser.parse(firstPollResponse);

        Thread.sleep(firstPollReply.getDelay() == 0 ? 1 : firstPollReply.getDelay());

        // 1st GET with polling URL
        String secondPollResponse = wisprService.poll(firstPollReply.getLoginResultsUrl()).execute().body();
        WisprPollReply secondPollReply = pollParser.parse(secondPollResponse);

        Thread.sleep(secondPollReply.getDelay() == 0 ? 1 : secondPollReply.getDelay());

        // 2nd HTTP GET with polling URL
        return wisprService.poll(secondPollReply.getLoginResultsUrl()).execute().body();
    }
}
