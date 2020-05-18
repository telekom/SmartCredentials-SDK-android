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

package de.telekom.smartcredentials.core.pushnotifications;

import android.support.annotation.IntRange;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class SmartCredentialsMessage {

    private String mDestination;
    private Map<String, String> mData;
    private String mMessageId;
    private String mMessageType;
    private int mTtl;
    private String mCollapseKey;

    private SmartCredentialsMessage(MessageBuilder builder) {
        this.mDestination = builder.destination;
        this.mData = builder.data;
        this.mMessageId = builder.messageId;
        this.mMessageType = builder.messageType;
        this.mTtl = builder.ttl;
        this.mCollapseKey = builder.collapseKey;
    }

    public String getDestination() {
        return mDestination;
    }

    public Map<String, String> getData() {
        return mData;
    }

    public String getMessageId() {
        return mMessageId;
    }

    public String getMessageType() {
        return mMessageType;
    }

    public int getTtl() {
        return mTtl;
    }

    public String getCollapseKey() {
        return mCollapseKey;
    }

    public static class MessageBuilder {
        private String destination;
        private Map<String, String> data;
        private String messageId;
        private String messageType;
        private int ttl;
        private String collapseKey;

        public MessageBuilder(String destination) {
            this.destination = destination;
            this.data = new ArrayMap<>();
        }

        public MessageBuilder addData(String var1, String var2) {
            this.data.put(var1, var2);
            return this;
        }

        public MessageBuilder setData(Map<String, String> var1) {
            this.data.clear();
            this.data.putAll(var1);
            return this;
        }

        public MessageBuilder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public MessageBuilder setMessageType(String messageType) {
            this.messageType = messageType;
            return this;
        }

        public MessageBuilder setTtl(@IntRange(from = 0L,to = 86400L) int ttl) {
            this.ttl = ttl;
            return this;
        }

        public MessageBuilder setCollapseKey(String collapseKey) {
            this.collapseKey = collapseKey;
            return this;
        }

        public SmartCredentialsMessage build() {
            return new SmartCredentialsMessage(this);
        }
    }
}
