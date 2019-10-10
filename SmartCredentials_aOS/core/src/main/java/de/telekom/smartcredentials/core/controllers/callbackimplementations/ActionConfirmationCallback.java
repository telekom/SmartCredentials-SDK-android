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

package de.telekom.smartcredentials.core.controllers.callbackimplementations;

import android.content.Context;

import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

@SuppressWarnings("unused")
public class ActionConfirmationCallback extends ExecutionCallback {

    private final SmartCredentialsAction mAction;
    private final ItemDomainModel mModel;
    private final ExecutionCallback mCallback;
    private final Context mContext;

    public ActionConfirmationCallback(Context context, SmartCredentialsAction action, ItemDomainModel model, ExecutionCallback callback) {
        mAction = action;
        mModel = model;
        mCallback = callback;
        mContext = context;
    }

    @Override
    public void onComplete(Object response) {
        mAction.execute(mContext, mModel, mCallback);
    }

    @Override
    public void onFailed(Object error) {

    }

    @Override
    public void onUnavailable(String error) {
    }

    @Override
    public void onAuthorizationRequired(Object object) {
        mCallback.onAuthorizationRequired(object);
    }
}
