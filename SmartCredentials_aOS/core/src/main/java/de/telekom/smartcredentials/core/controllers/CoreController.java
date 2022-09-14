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

package de.telekom.smartcredentials.core.controllers;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.util.List;
import java.util.Set;

import de.telekom.smartcredentials.core.actions.ActionSelector;
import de.telekom.smartcredentials.core.actions.ActionSelectorImpl;
import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsSystemPropertyMap;
import de.telekom.smartcredentials.core.controllers.callbackimplementations.ActionConfirmationCallback;
import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.exceptions.AppAliasNotSetException;
import de.telekom.smartcredentials.core.exceptions.ConfirmationError;
import de.telekom.smartcredentials.core.exceptions.UserNotSetException;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.utils.ActionsConverter;
import de.telekom.smartcredentials.core.model.utils.DefaultAction;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionApi;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.core.rootdetector.strategy.RootDetectionOptionListener;
import de.telekom.smartcredentials.core.storage.SecurityCompromisedSubject;

/**
 * Created by Lucian Iacob on November 15, 2018.
 */
public class CoreController extends SecurityCompromisedSubject implements CoreApi {

    public static final String UID_EXCEPTION_MESSAGE = "UID cannot be empty when trying to put an item";
    private static final String TAG = "CoreController";

    private final Context mContext;
    private String mUserId;
    private String mAppAlias;
    private final boolean mRootCheckerEnabled;
    private final Set<RootDetectionOption> mRootDetectionOptions;
    private final RootDetectionApi mRootDetectionApi;

    public CoreController(Context context, RootDetectionApi rootDetectionApi,
                          boolean rootCheckerEnabled, Set<RootDetectionOption> rootDetectionOptions) {
        mContext = context;
        mRootDetectionApi = rootDetectionApi;
        mRootCheckerEnabled = rootCheckerEnabled;
        mRootDetectionOptions = rootDetectionOptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Boolean> isDeviceRooted() {
        ApiLoggerResolver.logMethodAccess(TAG, "isDeviceRooted");
        return new SmartCredentialsResponse<>(mRootDetectionApi.isSecurityCompromised(mRootDetectionOptions));
    }

    @Override
    public SmartCredentialsApiResponse<Boolean> isDeviceRooted(RootDetectionOptionListener listener) {
        ApiLoggerResolver.logMethodAccess(TAG, "isDeviceRooted");
        return new SmartCredentialsResponse<>(mRootDetectionApi.isSecurityCompromised(mRootDetectionOptions, listener));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> execute(Context context, ItemEnvelope itemEnvelope, String actionId, ExecutionCallback callback) {
        ApiLoggerResolver.logMethodAccess(TAG, "execute");
        if (isSecurityCompromised()) {
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        execute(context, ModelConverter.toItemDomainModel(itemEnvelope), actionId, callback);
        return new SmartCredentialsResponse<>();
    }

    public boolean isDeviceRestricted(SmartCredentialsFeatureSet feature) {
        return SmartCredentialsSystemPropertyMap.isFeatureBlockedOnCurrentDevice(mContext, feature);
    }

    public String getUserId() {
        return mUserId;
    }

    public String getAppAlias() {
        return mAppAlias;
    }

    public void setUserId(String userId) {
        if (TextUtils.isEmpty(userId)) {
            throw new UserNotSetException();
        }
        mUserId = userId;
    }

    public void setAppAlias(String appAlias) {
        if (TextUtils.isEmpty(appAlias)) {
            throw new AppAliasNotSetException();
        }
        mAppAlias = appAlias;
    }

    public boolean isSecurityCompromised() {
        return mRootCheckerEnabled && mRootDetectionApi.isSecurityCompromised(mRootDetectionOptions);
    }

    public void handleSecurityCompromised() {
        notifySecurityCompromised();
        ApiLoggerResolver.logError(getClass().getSimpleName(), "SECURITY COMPROMISED!");
    }

    private void execute(Context context, @NonNull ItemDomainModel itemDomainModel, String actionId, ExecutionCallback executionCallback) {
        ItemDomainAction confirmationActionDomain = null;
        List<ItemDomainAction> actionList = itemDomainModel.getMetadata().getActionList();

        ActionSelector actionSelector = new ActionSelectorImpl();
        SmartCredentialsAction action = actionSelector.select(actionId, actionList);
        if (action == null) {
            executionCallback.onUnavailable(ActionSelector.ACTION_NOT_PROCESSED);
        } else {
            if (!action.getName().equals(DefaultAction.CONFIRMATION.getName())) {
                if (itemDomainModel.getMetadata().isAutoLockEnabled() || itemDomainModel.getMetadata().isLocked()) {
                    boolean hasConfirmationAction = false;
                    for (ItemDomainAction actionListItem : actionList) {
                        if (actionListItem.getName().equals(DefaultAction.CONFIRMATION.getName())) {
                            hasConfirmationAction = true;
                            confirmationActionDomain = actionListItem;
                        }
                    }
                    if (hasConfirmationAction) {
                        try {
                            SmartCredentialsAction confirmationAction = ActionsConverter.toSmartCredentialsAction(confirmationActionDomain);
                            confirmationAction.execute(context, itemDomainModel,
                                    new ActionConfirmationCallback(context, action, itemDomainModel, executionCallback));
                        } catch (JSONException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                            ApiLoggerResolver.logError(getClass().getName(), e.getMessage());
                        }
                    } else {
                        executionCallback.onFailed(ConfirmationError.NO_CONFIRMATION_ACTION);
                    }
                } else {
                    action.execute(context, itemDomainModel, executionCallback);
                }
            } else {
                action.execute(context, itemDomainModel, executionCallback);
            }
        }
    }
}
