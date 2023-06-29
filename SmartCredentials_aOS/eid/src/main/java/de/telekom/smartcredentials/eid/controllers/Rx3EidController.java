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

package de.telekom.smartcredentials.eid.controllers;

import android.content.Context;
import android.nfc.Tag;

import de.telekom.smartcredentials.core.api.rx.Rx3EidApi;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;
import de.telekom.smartcredentials.eid.rest.RetrofitClient;
import de.telekom.smartcredentials.eid.rx.BindRx3Completable;
import de.telekom.smartcredentials.eid.rx.MessagesRx3Observable;
import de.telekom.smartcredentials.eid.rx.SendCommandRx3Completable;
import de.telekom.smartcredentials.eid.rx.UnbindRx3Completable;
import de.telekom.smartcredentials.eid.rx.UpdateNfcTagRx3Completable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

/**
 * Created by Alex.Graur@endava.com at 3/31/2021
 */
public class Rx3EidController implements Rx3EidApi {

    private final EidController eidController;

    public Rx3EidController(EidController eidController) {
        this.eidController = eidController;
    }

    @Override
    public Completable bind(Context context, String appPackage) {
        return Completable.create(new BindRx3Completable(context, appPackage, eidController));
    }

    @Override
    public Completable unbind(Context context) {
        return Completable.create(new UnbindRx3Completable(context, eidController));
    }

    @Override
    public Observable<EidMessage> observeMessages() {
        return Observable.create(new MessagesRx3Observable(eidController)).share();
    }

    @Override
    public Completable sendCommand(EidCommand command) {
        return Completable.create(new SendCommandRx3Completable(command, eidController));
    }

    @Override
    public Completable updateNfcTag(Tag tag) {
        return Completable.create(new UpdateNfcTagRx3Completable(tag, eidController));
    }

    @Override
    public Completable observeLogFailure(String errorCode, String jwt, String os,
                                         String vendor, String model, String sicv,
                                         String reason, boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx3EidService(isProduction)
                .logFailure(errorCode, jwt, os, vendor, model, sicv, reason);
    }

    @Override
    public Observable<String> observeLoadingErrorCode(String jwt, boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx3EidService(isProduction).getError(jwt);
    }

    @Override
    public Observable<Boolean> observeCheckPatchLevel(String version, boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx3EidService(isProduction).checkPatchLevel(version);
    }
}
