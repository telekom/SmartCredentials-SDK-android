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

package de.telekom.smartcredentials.storage.domain.model.token;

import com.google.gson.Gson;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.storage.TokenRequest;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

public class SmartCredentialsTokenRequest implements TokenRequest {

    private final Gson mGson;
    private final String mType;
    private final EncryptionStrategy mEncryptionStrategy;
    private final boolean mIsSensitive;
    private String mEncryptedModel;

    public SmartCredentialsTokenRequest(Gson gson, String type,
                                        EncryptionStrategy encryptionStrategy, boolean isSensitive) {
        mGson = gson;
        mType = type;
        mEncryptionStrategy = encryptionStrategy;
        mIsSensitive = isSensitive;
    }

    @Override
    public String getEncryptedModel() {
        return mEncryptedModel;
    }

    public void setEncryptedModel(String encryptedModel) {
        mEncryptedModel = encryptedModel;
    }

    @Override
    public String getKey() throws EncryptionException {
        return getToken().getKey();
    }

    @Override
    public long getCounter() throws EncryptionException {
        if (getOtpType() == OTPType.TOTP) {
            return System.currentTimeMillis() / getValidityPeriodMillis();
        }
        return getToken().getCounter();
    }

    @Override
    public void setCounter(long counter) throws EncryptionException {
        getToken().setCounter(counter);
        Token token = mGson.fromJson(mEncryptionStrategy.decrypt(mEncryptedModel), Token.class);
        token = token == null ? new Token() : token;
        token.setCounter(counter);
        mEncryptedModel = mEncryptionStrategy.encrypt(mGson.toJson(token), mIsSensitive);
    }

    @Override
    public OTPType getOtpType() {
        return OTPType.getOTP(mType);
    }

    @Override
    public int getOtpValueDigitsCount() throws EncryptionException {
        return getToken().getOtpValueDigitsCount();
    }

    @Override
    public String getAlgorithm(String defaultAlgorithm) throws EncryptionException {
        return getToken().getAlgorithm(defaultAlgorithm);
    }

    @Override
    public boolean addChecksum() throws EncryptionException {
        return getToken().addChecksum();
    }

    @Override
    public int getTruncationOffset() throws EncryptionException {
        return getToken().getTruncationOffset();
    }

    @Override
    public long getValidityPeriodMillis() throws EncryptionException {
        return getToken().getValidityPeriodMillis();
    }

    private Token getToken() throws EncryptionException {
        Token token = mGson.fromJson(mEncryptionStrategy.decrypt(mEncryptedModel), Token.class);
        return token == null ? new Token() : token;
    }
}
