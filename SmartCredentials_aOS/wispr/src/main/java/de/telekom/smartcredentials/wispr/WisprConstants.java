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

package de.telekom.smartcredentials.wispr;

public interface WisprConstants {

    String BUTTON_PARAM = "button";
    String FNAME_PARAM = "FNAME";
    String ORIGINATING_SERVER_PARAM = "OriginatingServer";
    String WISPR_VERSION_PARAM = "WISPrVersion";

    int WISPR_RESPONSE_CODE_LOGIN_SUCCEEDED = 50;
    int WISPR_MESSAGE_TYPE_LOGOFF_SUCCEEDED = 130;
    int WISPR_RESPONSE_CODE_LOGOFF_SUCCEEDED = 150;
}
