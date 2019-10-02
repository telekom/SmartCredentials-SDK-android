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

package de.telekom.smartcredentials.wispr.exceptions;

public enum WisprExceptionReason {

    NO_WISPR_VERSION("WISPr version cannot be null."),
    NO_ONLINE_URL("WISPr online URL cannot be null"),
    NO_USERNAME("WISPr username cannot be null."),
    NO_PASSWORD("WISPr password cannot be null."),
    NO_TIMEOUT("WISPr timeout cannot be null."),
    NO_LOGOFF_URL("WISPr logoff URL cannot be null."),
    NO_STATUS_URL("WISPr status URL cannot be null."),
    NO_FOLLOW_REDIRECT("WISPr follow redirect cannot be null.");

    private final String mReason;


    WisprExceptionReason(String reason) {
        mReason = reason;
    }

    public String getReason() {
        return mReason;
    }
}
