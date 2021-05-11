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
import android.os.Handler;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.authorization.di.ObjectGraphCreatorAuthorization;
import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory;
import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;
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
    private FragmentActivity mActivity;
    private AuthorizationConfiguration mConfiguration;

    public ActionConfirmation() {
        // required empty constructor
    }

    public ActionConfirmation(FragmentActivity activity, AuthorizationConfiguration configuration,
                              String id, String name, JSONObject data) {
        super(id, name, data);
        this.mActivity = activity;
        this.mConfiguration = configuration;
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
                authorizationApi.authorize(mActivity, mConfiguration,
                        getAuthorizationCallback(storageApi, callback, ConfirmationType.DEFAULT_SYSTEM));
            }
        } catch (JSONException e) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), "Could not retrieve the confirmation type");
        }
    }

    private AuthorizationCallback getAuthorizationCallback(StorageApi storageApi, ExecutionCallback callback, ConfirmationType type) {
        return new AuthorizationCallback() {
            @Override
            public void onAuthorizationError(String error) {
                callback.onUnavailable(error);
            }

            @Override
            public void onAuthorizationFailed(String error) {
                callback.onFailed(new ActionConfirmationResponse(false, error, type));
            }

            @Override
            public void onAuthorizationSucceeded() {
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
