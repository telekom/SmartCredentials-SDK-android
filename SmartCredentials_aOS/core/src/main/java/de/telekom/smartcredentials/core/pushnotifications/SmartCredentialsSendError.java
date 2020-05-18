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

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class SmartCredentialsSendError {

    private String mMessageId;
    private Exception mException;

    public SmartCredentialsSendError(String mMessageId, Exception mException) {
        this.mMessageId = mMessageId;
        this.mException = mException;
    }

    public String getMessageId() {
        return mMessageId;
    }

    public Exception getException() {
        return mException;
    }
}
