/*
 * Copyright (c) 2021 Telekom Deutschland AG
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

package de.telekom.smartcredentials.core.api.rx;

import android.content.Context;
import android.nfc.Tag;

import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

/**
 * Created by Alex.Graur@endava.com at 3/31/2021
 */
public interface Rx3EidApi {

    Completable bind(Context context, String appPackage);

    Completable unbind(Context context);

    Observable<EidMessage> observeMessages();

    Completable sendCommand(EidCommand command);

    Completable updateNfcTag(Tag tag);
}
