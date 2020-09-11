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

package de.telekom.smartcredentials.authorization.actions;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.authorization.di.ObjectGraphCreatorAuthorization;
import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory;
import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
import de.telekom.smartcredentials.core.storage.UpdateItemTask;

@SuppressWarnings("unused")
public class ActionConfirmation extends SmartCredentialsAction {

    @SuppressWarnings("WeakerAccess")
    public static final String CONFIRMATION_TYPE = "confirmation_type";

    private ItemEnvelope mItemEnvelope;
    private Fragment mAuthorizationDialog;
    private BiometricAuthorizationPresenter mBiometricsAuthorizationPresenter;

    public ActionConfirmation() {
        // required empty constructor
    }

    public ActionConfirmation(String id, String name, JSONObject data) {
        super(id, name, data);
    }

    @Override
    public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {
        StorageApi storageApi = ObjectGraphCreatorAuthorization.getInstance().getStorageApi();
        try {
            mItemEnvelope = ModelConverter.toItemEnvelope(itemDomainModel);
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not convert ItemDomain to ItemEnvelope");
        }

        try {
            String confirmationType = getData().getString(CONFIRMATION_TYPE);
            if (confirmationType.equals(ConfirmationType.DEFAULT_SYSTEM.name())) {
                AuthorizationApi authorizationApi = SmartCredentialsAuthorizationFactory
                        .getAuthorizationApi();
                callback.onAuthorizationRequired(Build.VERSION.SDK_INT < Build.VERSION_CODES.P ?
                        authorizationApi.getAuthorizeUserFragment(getAuthorizationCallback(storageApi, callback, ConfirmationType.DEFAULT_SYSTEM)) :
                        authorizationApi.getBiometricAuthorizationPresenter(getAuthorizationCallback(storageApi, callback, ConfirmationType.DEFAULT_SYSTEM)));
            }
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not retrieve the confirmation type");
        }
    }

    private AuthorizationCallback getAuthorizationCallback(StorageApi storageApi, ExecutionCallback callback, ConfirmationType type) {
        return new AuthorizationCallback() {
            @Override
            public void onUnavailable(AuthorizationPluginUnavailable errorMessage) {
                callback.onUnavailable(errorMessage.toString());
            }

            @Override
            public void onFailure(AuthorizationPluginError message) {
                callback.onFailed(new ActionConfirmationResponse(false, message.toString(), type));
            }

            @Override
            public void onAuthorized() {
                new Handler().postDelayed(() -> {
                    callback.onComplete(new ActionConfirmationResponse(true, "Authorization successful", type));
                    updateItem(storageApi);
                }, 300);
            }
        };
    }

    private void updateItem(StorageApi api) {
        ItemContext itemContext = ItemContextFactory.getItemContext(mItemEnvelope.isSensitiveItem(),
                mItemEnvelope.getItemMetadata().isDataEncrypted(), mItemEnvelope.getItemType().getDesc());
        mItemEnvelope.getItemMetadata().setLockState(false);
        UpdateItemTask updateItemTask = new UpdateItemTask(api, itemContext, mItemEnvelope);
        updateItemTask.execute();
    }
}
