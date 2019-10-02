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

package de.telekom.smartcredentials.wispr.parsers;

import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.replies.WisprAuthenticationReply;

public interface WisprAuthenticationParser {

    String WISPR_RESPONSE_PREFIX = "<WISPAccessGatewayParam";
    String WISPR_RESPONSE_POSTFIX = "</WISPAccessGatewayParam>";

    WisprAuthenticationReply parse(String body) throws WisprException;
}
