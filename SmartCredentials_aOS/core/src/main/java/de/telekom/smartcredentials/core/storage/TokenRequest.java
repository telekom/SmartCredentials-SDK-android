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

package de.telekom.smartcredentials.core.storage;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.otp.OTPType;

public interface TokenRequest {

    long getCounter() throws EncryptionException;

    void setCounter(long counter) throws EncryptionException;

    int getOtpValueDigitsCount() throws EncryptionException;

    boolean addChecksum() throws EncryptionException;

    int getTruncationOffset() throws EncryptionException;

    String getAlgorithm(String defaultAlgorithm) throws EncryptionException;

    String getKey() throws EncryptionException;

    long getValidityPeriodMillis() throws EncryptionException;

    String getEncryptedModel();

    OTPType getOtpType();
}
