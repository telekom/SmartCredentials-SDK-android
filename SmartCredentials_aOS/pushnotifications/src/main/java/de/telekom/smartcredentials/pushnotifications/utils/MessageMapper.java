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

package de.telekom.smartcredentials.pushnotifications.utils;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsMessage;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class MessageMapper {

    public RemoteMessage mapToRemoteMessage(SmartCredentialsMessage message){
        if(message.getDestination().isEmpty()){
            throw new RuntimeException("invalid message destination");
        } else {
            RemoteMessage.Builder remoteMessageBuilder = new RemoteMessage.Builder(message.getDestination());
            if(message.getData() != null){
                remoteMessageBuilder.setData(message.getData());
            }
            if(message.getMessageId() != null){
                remoteMessageBuilder.setMessageId(message.getMessageId());
            }
            if(message.getMessageType() != null){
                remoteMessageBuilder.setMessageType(message.getMessageType());
            }
            if(message.getTtl() != 0){
                remoteMessageBuilder.setTtl(message.getTtl());
            }
            if(message.getCollapseKey() != null){
                remoteMessageBuilder.setCollapseKey(message.getCollapseKey());
            }
            return remoteMessageBuilder.build();
        }
    }
}
