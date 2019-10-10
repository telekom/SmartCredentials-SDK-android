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

package de.telekom.smartcredentials.core.model.utils;

public enum DefaultAction {
    CALL_SERVICE("de.telekom.smartcredentials.networking.actions.ActionCallService"),
    QR_LOGIN("de.telekom.smartcredentials.qrlogin.actions.ActionQrLogin"),
    ITEM_TO_JSON("de.telekom.smartcredentials.core.actions.ActionItemToJSON"),
    CONFIRMATION("de.telekom.smartcredentials.authorization.actions.ActionConfirmation");

    private final String mName;

    DefaultAction(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
