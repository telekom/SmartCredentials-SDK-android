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
public class WisprRedirectReply {

    private static final String PATH = "Redirect";

    @Path(PATH)
    @Element(name = "AccessProcedure", required = false)
    private String mAccessProcedure;

    @Path(PATH)
    @Element(name = "AccessLocation", required = false)
    private String mAccessLocation;

    @Path(PATH)
    @Element(name = "LocationName", required = false)
    private String mLocationName;

    @Path(PATH)
    @Element(name = "LoginURL", required = false)
    private String mLoginUrl;

    @Path(PATH)
    @Element(name = "AbortLoginURL", required = false)
    private String mAbortLoginUrl;

    @Path(PATH)
    @Element(name = "MessageType")
    private int mMessageType;

    @Path(PATH)
    @Element(name = "ResponseCode")
    private int mResponseCode;

    @Path(PATH)
    @Element(name = "VersionLow", required = false)
    private float mVersionLow;

    @Path(PATH)
    @Element(name = "VersionHigh", required = false)
    private float mVersionHigh;

    @Path(PATH)
    @Element(name = "EAPMsg", required = false)
    private String mEapMessage;

    public String getAccessProcedure() {
        return mAccessProcedure;
    }

    public String getAccessLocation() {
        return mAccessLocation;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public String getLoginUrl() {
        return mLoginUrl;
    }

    public String getAbortLoginUrl() {
        return mAbortLoginUrl;
    }

    public int getMessageType() {
        return mMessageType;
    }

    public int getResponseCode() {
        return mResponseCode;
    }

    public float getVersionLow() {
        return mVersionLow;
    }

    public float getVersionHigh() {
        return mVersionHigh;
    }

    public String getmEapMessage() {
        return mEapMessage;
    }

    @Override
    public String toString() {
        return "WisprRedirectReply{" +
                "mAccessProcedure='" + mAccessProcedure + '\'' +
                ", mAccessLocation='" + mAccessLocation + '\'' +
                ", mLocationName='" + mLocationName + '\'' +
                ", mLoginUrl='" + mLoginUrl + '\'' +
                ", mAbortLoginUrl='" + mAbortLoginUrl + '\'' +
                ", mMessageType=" + mMessageType +
                ", mResponseCode=" + mResponseCode +
                ", mVersionLow=" + mVersionLow +
                ", mVersionHigh=" + mVersionHigh +
                ", mEapMessage=" + mEapMessage +
                '}';
    }
}
