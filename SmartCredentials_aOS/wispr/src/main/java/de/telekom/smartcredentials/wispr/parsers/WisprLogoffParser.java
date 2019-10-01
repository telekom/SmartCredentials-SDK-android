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

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import de.telekom.smartcredentials.core.wispr.callbacks.WisprLogoffCallback;
import de.telekom.smartcredentials.wispr.exceptions.WisprException;
import de.telekom.smartcredentials.wispr.replies.WisprLogoffReply;

import static de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationParser.WISPR_RESPONSE_POSTFIX;
import static de.telekom.smartcredentials.wispr.parsers.WisprAuthenticationParser.WISPR_RESPONSE_PREFIX;

public class WisprLogoffParser {

    private WisprLogoffCallback mLogoffCallback;

    public WisprLogoffParser(WisprLogoffCallback logoffCallback) {
        mLogoffCallback = logoffCallback;
    }

    public WisprLogoffReply parse(String body) throws WisprException {
        int prefixIndex = body.indexOf(WISPR_RESPONSE_PREFIX);
        int postfixIndex = body.indexOf(WISPR_RESPONSE_POSTFIX);

        if (prefixIndex < 0 || postfixIndex < 0 || prefixIndex > postfixIndex) {
            throw new WisprException("Unexpected WISPr redirect response format.");
        }

        String xml = body.substring(prefixIndex, postfixIndex + WISPR_RESPONSE_POSTFIX.length());
        mLogoffCallback.onDebugged(xml);
        final Serializer serializer = new Persister();

        WisprLogoffReply wisprLogoffReply;
        try {
            wisprLogoffReply = serializer.read(WisprLogoffReply.class, xml, false);
        } catch (Exception e) {
            throw new WisprException("WISPr redirect response parsing exception.");
        }

        if (wisprLogoffReply == null) {
            throw new WisprException("Could not deserialize WISPr redirect response.");
        } else {
            return wisprLogoffReply;
        }
    }
}
