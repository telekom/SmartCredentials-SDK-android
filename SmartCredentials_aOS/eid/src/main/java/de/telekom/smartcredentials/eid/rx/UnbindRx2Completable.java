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

import android.content.Context;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.eid.controllers.EidController;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by Alex.Graur@endava.com at 3/30/2021
 */
public class UnbindRx2Completable implements CompletableOnSubscribe {

    private final Context context;
    private final EidController eidController;

    public UnbindRx2Completable(Context context, EidController eidController) {
        this.context = context;
        this.eidController = eidController;
    }

    @Override
    public void subscribe(@NonNull CompletableEmitter emitter) {
        SmartCredentialsApiResponse<Void> response = eidController.unbind(context);

        if (response.isSuccessful()) {
            emitter.onComplete();
        } else {
            emitter.onError(response.getError());
        }
    }
}
