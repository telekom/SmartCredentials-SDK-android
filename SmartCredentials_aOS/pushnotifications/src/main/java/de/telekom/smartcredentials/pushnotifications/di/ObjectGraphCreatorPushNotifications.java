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

package de.telekom.smartcredentials.pushnotifications.di;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsSendError;
import de.telekom.smartcredentials.pushnotifications.models.FirebaseRemoteMessage;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class ObjectGraphCreatorPushNotifications {

    private static ObjectGraphCreatorPushNotifications sInstance;

    private boolean mSubscriptionState;
    private MutableLiveData<String> mTokenData;
    private MutableLiveData<SmartCredentialsRemoteMessage> mRemoteMessageData;
    private MutableLiveData<String> mSentMessageIdData;
    private MutableLiveData<SmartCredentialsSendError> mSendErrorData;

    private ObjectGraphCreatorPushNotifications() {
        mTokenData = new MutableLiveData<>();
        mRemoteMessageData = new MutableLiveData<>();
        mSentMessageIdData = new MutableLiveData<>();
        mSendErrorData = new MutableLiveData<>();
    }

    public static ObjectGraphCreatorPushNotifications getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorPushNotifications();
        }
        return sInstance;
    }

    @NonNull
    public PushNotificationsController provideApiControllerPushNotifications(CoreController coreController) {
        return new PushNotificationsController(coreController);
    }

    public MutableLiveData<String> getTokenData() {
        return mTokenData;
    }

    public void setTokenData(String token) {
        mTokenData.postValue(token);
    }

    public MutableLiveData<SmartCredentialsRemoteMessage> getRemoteMessageData() {
        return mRemoteMessageData;
    }

    public void setRemoteMessageData(FirebaseRemoteMessage firebaseRemoteMessage) {
        mRemoteMessageData.postValue(firebaseRemoteMessage);
    }

    public void setSentMessageId(String messageId) {
        mSentMessageIdData.postValue(messageId);
    }

    public MutableLiveData<String> getSentMessageIdData() {
        return mSentMessageIdData;
    }

    public void setSendErrorData(SmartCredentialsSendError sendError){
        mSendErrorData.postValue(sendError);
    }

    public LiveData<SmartCredentialsSendError> getSendErrorData() {
        return mSendErrorData;
    }

    public void setSubscriptionState(boolean state) {
        mSubscriptionState = state;
    }

    public boolean isSubscribed() {
        return mSubscriptionState;
    }

    public static void destroy() {
        sInstance = null;
    }
}
