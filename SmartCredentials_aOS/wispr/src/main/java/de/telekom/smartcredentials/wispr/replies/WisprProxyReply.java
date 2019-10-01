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
public class WisprProxyReply {

    private static final String PATH = "Proxy";

    @Path(PATH)
    @Element(name = "MessageType")
    private int mMessageType;

    @Path(PATH)
    @Element(name = "ResponseCode")
    private int mResponseCode;

    @Path(PATH)
    @Element(name = "NextURL", required = false)
    private String mNextUrl;

    @Path(PATH)
    @Element(name = "Delay", required = false)
    private int mDelay;

    public int getMessageType() {
        return mMessageType;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public String getNextUrl() {
        return mNextUrl;
    }

    public int getDelay() {
        return mDelay;
    }

    @Override
    public String toString() {
        return "WisprProxyReply{" +
                "mMessageType=" + mMessageType +
                ", mResponseCode=" + mResponseCode +
                ", mNextUrl='" + mNextUrl + '\'' +
                ", mDelay=" + mDelay +
                '}';
    }
}
