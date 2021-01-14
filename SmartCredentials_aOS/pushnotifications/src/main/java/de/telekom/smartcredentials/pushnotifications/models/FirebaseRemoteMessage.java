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

package de.telekom.smartcredentials.pushnotifications.models;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsNotification;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class FirebaseRemoteMessage extends SmartCredentialsRemoteMessage {

    private RemoteMessage mRemoteMessage;

    public FirebaseRemoteMessage(RemoteMessage remoteMessage){
        mRemoteMessage = remoteMessage;
    }

    @Override
    public SmartCredentialsNotification getNotification() {
        return new FirebaseNotification(mRemoteMessage.getNotification());
    }

    @Override
    public String getCollapseKey() {
        return mRemoteMessage.getCollapseKey();
    }

    @Override
    public Map<String, String> getData() {
        return mRemoteMessage.getData();
    }

    @Override
    public String getFrom() {
        return mRemoteMessage.getFrom();
    }

    @Override
    public String getMessageId() {
        return mRemoteMessage.getMessageId();
    }

    @Override
    public String getMessageType() {
        return mRemoteMessage.getMessageType();
    }

    @Override
    public int getOriginalPriority() {
        return mRemoteMessage.getOriginalPriority();
    }

    @Override
    public int getPriority() {
        return mRemoteMessage.getPriority();
    }

    @Override
    public long getSentTime() {
        return mRemoteMessage.getSentTime();
    }

    @Override
    public String getTo() {
        return mRemoteMessage.getTo();
    }

    @Override
    public int getTtl() {
        return mRemoteMessage.getTtl();
    }
}
