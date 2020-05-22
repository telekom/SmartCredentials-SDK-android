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

import java.util.List;

import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

/**
 * Created by gabriel.blaj@endava.com at 5/21/2020
 */
public class UnsubscribeCompletable implements CompletableOnSubscribe {

    private final PushNotificationsController mController;

    public UnsubscribeCompletable(PushNotificationsController controller) {
        mController = controller;
    }

    @Override
    public void subscribe(CompletableEmitter emitter) {
        mController.unsubscribe(new PushNotificationsCallback() {
            @Override
            public void onSuccess(String message) {
                emitter.onComplete();
            }

            @Override
            public void onFailure(String message, List<PushNotificationsError> errors) {
                emitter.onError(new Exception(message));
            }
        });
    }
}
