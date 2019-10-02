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

package de.telekom.smartcredentials.wispr.replies;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "WISPAccessGatewayParam")
public class WisprPollReply {

    private static final String PATH = "PollNotification";

    @Path(PATH)
    @Element(name = "MessageType")
    private String mMessageType;

    @Path(PATH)
    @Element(name = "ResponseCode")
    private String mResponseCode;

    @Path(PATH)
    @Element(name = "LoginResultsURL", required = false)
    private String mLoginResultsUrl;

    @Path(PATH)
    @Element(name = "Delay", required = false)
    private int mDelay;

    public String getMessageType() {
        return mMessageType;
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public String getLoginResultsUrl() {
        return mLoginResultsUrl;
    }

    public int getDelay() {
        return mDelay;
    }

    @Override
    public String toString() {
        return "WisprPollReply{" +
                "mMessageType='" + mMessageType + '\'' +
                ", mResponseCode='" + mResponseCode + '\'' +
                ", mLoginResultsUrl='" + mLoginResultsUrl + '\'' +
                '}';
    }
}
