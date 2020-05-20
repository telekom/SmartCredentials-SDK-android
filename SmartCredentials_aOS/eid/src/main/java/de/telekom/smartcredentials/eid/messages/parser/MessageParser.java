/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.messages.parser;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.eid.callbacks.EidMessageReceivedCallback;
import de.telekom.smartcredentials.eid.messages.AccessRightsMessage;
import de.telekom.smartcredentials.eid.messages.ApiLevelMessage;
import de.telekom.smartcredentials.eid.messages.AuthMessage;
import de.telekom.smartcredentials.eid.messages.BadStateMessage;
import de.telekom.smartcredentials.eid.messages.CertificateMessage;
import de.telekom.smartcredentials.eid.messages.EnterCanMessage;
import de.telekom.smartcredentials.eid.messages.EnterPinMessage;
import de.telekom.smartcredentials.eid.messages.EnterPukMessage;
import de.telekom.smartcredentials.eid.messages.InfoMessage;
import de.telekom.smartcredentials.eid.messages.InsertCardMessage;
import de.telekom.smartcredentials.eid.messages.InternalErrorMessage;
import de.telekom.smartcredentials.eid.messages.InvalidMessage;
import de.telekom.smartcredentials.eid.messages.ReaderListMessage;
import de.telekom.smartcredentials.eid.messages.ReaderMessage;
import de.telekom.smartcredentials.eid.messages.SmartEidMessage;
import de.telekom.smartcredentials.eid.messages.UnknownCommandMessage;
import de.telekom.smartcredentials.eid.messages.types.EidMessageType;

/**
 * Created by Alex.Graur@endava.com at 11/11/2019
 */
public class MessageParser {

    private final Gson mGson;
    private final EidMessageReceivedCallback mCallback;

    public MessageParser(EidMessageReceivedCallback callback) {
        mGson = new Gson();
        mCallback = callback;
    }

    public void parseMessage(String rawMessage) {
        if (mCallback != null) {
            SmartEidMessage message = mGson.fromJson(rawMessage, SmartEidMessage.class);
            EidMessageType messageType = EidMessageType.valueOf(message.getMessageType());
            switch (messageType) {
                case ACCESS_RIGHTS:
                    AccessRightsMessage accessRightsMessage = mGson.fromJson(rawMessage, AccessRightsMessage.class);
                    mCallback.onMessageReceived(accessRightsMessage);
                    break;
                case API_LEVEL:
                    ApiLevelMessage apiLevelMessage = mGson.fromJson(rawMessage, ApiLevelMessage.class);
                    mCallback.onMessageReceived(apiLevelMessage);
                    break;
                case AUTH:
                    AuthMessage authMessage = mGson.fromJson(rawMessage, AuthMessage.class);
                    mCallback.onMessageReceived(authMessage);
                    break;
                case BAD_STATE:
                    BadStateMessage badStateMessage = mGson.fromJson(rawMessage, BadStateMessage.class);
                    mCallback.onMessageReceived(badStateMessage);
                    break;
                case CERTIFICATE:
                    CertificateMessage certificateMessage = mGson.fromJson(rawMessage, CertificateMessage.class);
                    mCallback.onMessageReceived(certificateMessage);
                    break;
                case ENTER_CAN:
                    EnterCanMessage enterCanMessage = mGson.fromJson(rawMessage, EnterCanMessage.class);
                    mCallback.onMessageReceived(enterCanMessage);
                    break;
                case ENTER_PIN:
                    EnterPinMessage enterPinMessage = mGson.fromJson(rawMessage, EnterPinMessage.class);
                    mCallback.onMessageReceived(enterPinMessage);
                    break;
                case ENTER_PUK:
                    EnterPukMessage enterPukMessage = mGson.fromJson(rawMessage, EnterPukMessage.class);
                    mCallback.onMessageReceived(enterPukMessage);
                    break;
                case INFO:
                    InfoMessage infoMessage = mGson.fromJson(rawMessage, InfoMessage.class);
                    mCallback.onMessageReceived(infoMessage);
                    break;
                case INSERT_CARD:
                    InsertCardMessage insertCardMessage = mGson.fromJson(rawMessage, InsertCardMessage.class);
                    mCallback.onMessageReceived(insertCardMessage);
                    break;
                case INTERNAL_ERROR:
                    InternalErrorMessage internalErrorMessage = mGson.fromJson(rawMessage, InternalErrorMessage.class);
                    mCallback.onMessageReceived(internalErrorMessage);
                    break;
                case INVALID:
                    InvalidMessage invalidMessage = mGson.fromJson(rawMessage, InvalidMessage.class);
                    mCallback.onMessageReceived(invalidMessage);
                    break;
                case READER:
                    ReaderMessage readerMessage = mGson.fromJson(rawMessage, ReaderMessage.class);
                    mCallback.onMessageReceived(readerMessage);
                    break;
                case READER_LIST:
                    ReaderListMessage readerListMessage = mGson.fromJson(rawMessage, ReaderListMessage.class);
                    mCallback.onMessageReceived(readerListMessage);
                    break;
                case UNKNOWN_COMMAND:
                    UnknownCommandMessage unknownCommandMessage = mGson.fromJson(rawMessage, UnknownCommandMessage.class);
                    mCallback.onMessageReceived(unknownCommandMessage);
                    break;
                default:
                    mCallback.onMessageReceived(message);
                    break;
            }
        }
    }
}
