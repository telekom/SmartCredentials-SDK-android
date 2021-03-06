/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.messages;

import de.telekom.smartcredentials.eid.messages.types.EidMessageType;

/**
 * Created by Alex.Graur@endava.com at 2/18/2020
 */
public class SdkNotConnectedMessage extends SmartEidMessage {

    public SdkNotConnectedMessage() {
        super(EidMessageType.SDK_NOT_CONNECTED.getMessageType());
    }
}
