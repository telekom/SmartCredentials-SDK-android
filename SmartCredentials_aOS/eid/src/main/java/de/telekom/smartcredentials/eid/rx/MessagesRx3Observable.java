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

package de.telekom.smartcredentials.eid.rx;

import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;
import de.telekom.smartcredentials.eid.controllers.EidController;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * Created by Alex.Graur@endava.com at 3/31/2021
 */
public class MessagesRx3Observable implements ObservableOnSubscribe<EidMessage> {

    private final EidController eidController;

    public MessagesRx3Observable(EidController eidController) {
        this.eidController = eidController;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<EidMessage> emitter) {
        eidController.setMessageReceiverCallback(new EidMessageReceivedCallback() {
            @Override
            public <M extends EidMessage> void onMessageReceived(M message) {
                emitter.onNext(message);
            }

            @Override
            public void onFailed(Throwable throwable) {
                emitter.onError(throwable);
            }
        });
    }
}
