/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.pushnotifications.rx;

import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by gabriel.blaj@endava.com at 5/21/2020
 */
public class MessageReceivedObservable implements ObservableOnSubscribe<SmartCredentialsRemoteMessage> {

    private final PushNotificationsController mController;

    public MessageReceivedObservable(PushNotificationsController controller) {
        mController = controller;
    }

    @Override
    public void subscribe(ObservableEmitter<SmartCredentialsRemoteMessage> emitter) {
        mController.setMessageReceivedCallback(emitter::onNext);
    }
}

