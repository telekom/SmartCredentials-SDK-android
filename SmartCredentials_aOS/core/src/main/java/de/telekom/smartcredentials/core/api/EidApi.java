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

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;

import de.telekom.smartcredentials.core.eid.EidBindCallback;
import de.telekom.smartcredentials.core.eid.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.EidSendCommandCallback;
import de.telekom.smartcredentials.core.eid.EidUpdateTagCallback;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public interface EidApi {

    void enableDispatcher(Activity activity);

    void disableDispatcher(Activity activity);

    void bind(Context context, String appPackage, EidBindCallback bindCallback,
              EidMessageReceivedCallback receiveMessageCallback);

    void unbind(Context context);

    void sendCommand(EidCommand command, EidSendCommandCallback callback);

    void updateNfcTag(Tag tag, EidUpdateTagCallback callback);
}
