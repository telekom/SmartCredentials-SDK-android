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

package de.telekom.smartcredentials.otp.task;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.otp.OTPHandlerCallback;

public class TaskManager {

    public InitOTPTask getInitOTPTask(SecurityApi securityApi, StorageApi storageApi,
                                      OTPHandlerCallback otpHandlerCallback, ItemDomainModel itemDomainModel) {
        return (new InitOTPTask(securityApi, storageApi, otpHandlerCallback, itemDomainModel));
    }
}
