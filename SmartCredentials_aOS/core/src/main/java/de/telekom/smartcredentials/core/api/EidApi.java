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

package de.telekom.smartcredentials.core.api;

import android.content.Context;
import android.nfc.Tag;

import de.telekom.smartcredentials.core.eid.callbacks.EidErrorReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidPatchLevelCheckCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.callbacks.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public interface EidApi {

    SmartCredentialsApiResponse<Void> bind(Context context, String appPackage);

    SmartCredentialsApiResponse<Void> unbind(Context context);

    SmartCredentialsApiResponse<Void> setMessageReceiverCallback(EidMessageReceivedCallback callback);

    <T extends EidCommand> SmartCredentialsApiResponse<Void> sendCommand(T command, EidSendCommandCallback callback);

    SmartCredentialsApiResponse<Void> updateNfcTag(Tag tag, EidUpdateTagCallback callback);

    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Void> retrieveLoadingErrorCode(String jwt, boolean isProduction, EidErrorReceivedCallback callback);

    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Void> checkPatchLevel(String version, boolean isProduction, EidPatchLevelCheckCallback callback);
}
