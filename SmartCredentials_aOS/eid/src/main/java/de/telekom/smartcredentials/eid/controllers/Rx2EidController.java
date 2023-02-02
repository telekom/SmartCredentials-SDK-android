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

import de.telekom.smartcredentials.core.api.rx.Rx2EidApi;
import de.telekom.smartcredentials.core.eid.commands.EidCommand;
import de.telekom.smartcredentials.core.eid.messages.EidMessage;
import de.telekom.smartcredentials.eid.rest.RetrofitClient;
import de.telekom.smartcredentials.eid.rx.BindRx2Completable;
import de.telekom.smartcredentials.eid.rx.MessagesRx2Observable;
import de.telekom.smartcredentials.eid.rx.SendCommandRx2Completable;
import de.telekom.smartcredentials.eid.rx.UnbindRx2Completable;
import de.telekom.smartcredentials.eid.rx.UpdateNfcTagRx2Completable;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by Alex.Graur@endava.com at 3/30/2021
 */
public class Rx2EidController implements Rx2EidApi {

    private final EidController eidController;

    public Rx2EidController(EidController eidController) {
        this.eidController = eidController;
    }

    @Override
    public Completable bind(Context context, String appPackage) {
        return Completable.create(new BindRx2Completable(context, appPackage, eidController));
    }

    @Override
    public Completable unbind(Context context) {
        return Completable.create(new UnbindRx2Completable(context, eidController));
    }

    @Override
    public Observable<EidMessage> observeMessages() {
        return Observable.create(new MessagesRx2Observable(eidController)).share();
    }

    @Override
    public Completable sendCommand(EidCommand command) {
        return Completable.create(new SendCommandRx2Completable(command, eidController));
    }

    @Override
    public Completable updateNfcTag(Tag tag) {
        return Completable.create(new UpdateNfcTagRx2Completable(tag, eidController));
    }

    @Override
    public Completable observeLogFailure(String errorCode, String jwt, String os,
                                              String vendor, String model, String sicv,
                                              boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx2EidService(isProduction)
                .logFailure(errorCode, jwt, os, vendor, model, sicv);
    }

    @Override
    public Observable<String> observeLoadingErrorCode(String jwt, boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx2EidService(isProduction).getError(jwt);
    }

    @Override
    public Observable<Boolean> observeCheckPatchLevel(String version, boolean isProduction) {
        RetrofitClient retrofitClient = new RetrofitClient(eidController.getEidConfiguration());
        return retrofitClient.getRx2EidService(isProduction).checkPatchLevel(version);
    }
}
