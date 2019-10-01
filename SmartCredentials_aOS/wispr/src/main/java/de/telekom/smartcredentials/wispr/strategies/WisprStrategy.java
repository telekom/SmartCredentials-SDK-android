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

package de.telekom.smartcredentials.wispr.strategies;

import java.io.IOException;
import java.util.Map;

import de.telekom.smartcredentials.core.wispr.callbacks.WisprAuthenticationCallback;
import de.telekom.smartcredentials.core.wispr.WisprVersion;
import de.telekom.smartcredentials.wispr.callbacks.WisprRedirectCallback;
import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationParser;
import de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationPollParser;
import de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationSimpleParser;
import de.telekom.smartcredentials.wispr.replies.WisprRedirectReply;
import de.telekom.smartcredentials.wispr.services.WisprService;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationEapPollingStrategy;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationEapStrategy;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationFonStrategy;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationOneStrategy;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationPollingStrategy;
import de.telekom.smartcredentials.wispr.strategies.authentication.WisprAuthenticationTwoStrategy;
import de.telekom.smartcredentials.wispr.strategies.online.WisprOnlineEapStrategy;
import de.telekom.smartcredentials.wispr.strategies.online.WisprOnlineFonStrategy;
import de.telekom.smartcredentials.wispr.strategies.online.WisprOnlineOneTwoStrategy;
import de.telekom.smartcredentials.wispr.strategies.online.WisprOnlineProxyStrategy;
import de.telekom.smartcredentials.wispr.strategies.online.WisprOnlineStrategy;
import de.telekom.smartcredentials.wispr.strategies.parameters.WisprParametersFonStrategy;
import de.telekom.smartcredentials.wispr.strategies.parameters.WisprParametersOneStrategy;
import de.telekom.smartcredentials.wispr.strategies.parameters.WisprParametersStrategy;
import de.telekom.smartcredentials.wispr.strategies.parameters.WisprParametersTwoStrategy;

public class WisprStrategy {

    private WisprVersion mWisprVersion;

    public WisprStrategy(String wisprVersion) throws IllegalArgumentException {
        mWisprVersion = WisprVersion.valueOf(WisprVersion.class, wisprVersion);
    }

    public String login(WisprService wisprService, String url, String username, String password,
                        String originatingServer, String eapMessage,
                        Map<String, String> loginParameters, WisprAuthenticationCallback authenticationCallback) throws IOException, WisprException, InterruptedException {
        switch (mWisprVersion) {
            case WISPR_1_WITH_POLLING:
            case WISPR_2_WITH_POLLING:
                return new WisprAuthenticationPollingStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(loginParameters, originatingServer));
            case WISPR_1_FON:
                return new WisprAuthenticationFonStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(null, null));
            case WISPR_2_WITH_EAP:
                return new WisprAuthenticationEapStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(null, null));
            case WISPR_2_WITH_EAP_AND_POLLING:
                return new WisprAuthenticationEapPollingStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(null, null));
            case WISPR_1:
            case WISPR_1_WITH_PROXY:
                return new WisprAuthenticationOneStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(loginParameters, originatingServer));
            case WISPR_2:
            case WISPR_2_WITH_PROXY:
            default:
                return new WisprAuthenticationTwoStrategy(authenticationCallback)
                        .login(wisprService, url, username, password, eapMessage,
                                generateLoginParameters(null, null));
        }
    }

    public WisprRedirectReply checkOnline(WisprService wisprService, String url,
                                          WisprRedirectCallback redirectCallback) throws IOException, WisprException {
        WisprOnlineStrategy wisprOnlineStrategy;
        switch (mWisprVersion) {
            case WISPR_1_WITH_PROXY:
            case WISPR_2_WITH_PROXY:
                wisprOnlineStrategy = new WisprOnlineProxyStrategy(redirectCallback);
                break;
            case WISPR_1_FON:
                wisprOnlineStrategy = new WisprOnlineFonStrategy(redirectCallback);
                break;
            case WISPR_2_WITH_EAP:
            case WISPR_2_WITH_EAP_AND_POLLING:
                wisprOnlineStrategy = new WisprOnlineEapStrategy(redirectCallback);
                break;
            case WISPR_1:
            case WISPR_1_WITH_POLLING:
            case WISPR_2:
            case WISPR_2_WITH_POLLING:
            default:
                wisprOnlineStrategy = new WisprOnlineOneTwoStrategy(redirectCallback);
                break;
        }
        return wisprOnlineStrategy.checkOnline(wisprService, url);
    }

    public WisprAuthenticationParser getAuthenticationParser(WisprAuthenticationCallback authenticationCallback) {
        switch (mWisprVersion) {
            case WISPR_1_FON:
            case WISPR_1_WITH_POLLING:
                return new WisprAuthenticationPollParser(authenticationCallback);
            default:
                return new WisprAuthenticationSimpleParser(authenticationCallback);
        }
    }

    private Map<String, String> generateLoginParameters(Map<String, String> loginParameters,
                                                        String originatingServer) {
        WisprParametersStrategy wisprParametersStrategy;
        switch (mWisprVersion) {
            case WISPR_1:
            case WISPR_1_WITH_PROXY:
            case WISPR_1_WITH_POLLING:
                wisprParametersStrategy = new WisprParametersOneStrategy(loginParameters, originatingServer);
                break;
            case WISPR_1_FON:
                wisprParametersStrategy = new WisprParametersFonStrategy(loginParameters);
                break;
            case WISPR_2_WITH_EAP:
            case WISPR_2_WITH_EAP_AND_POLLING:
            case WISPR_2:
            case WISPR_2_WITH_POLLING:
            case WISPR_2_WITH_PROXY:
            default:
                wisprParametersStrategy = new WisprParametersTwoStrategy(loginParameters);
                break;
        }
        return wisprParametersStrategy.getWisprParameters();
    }
}
