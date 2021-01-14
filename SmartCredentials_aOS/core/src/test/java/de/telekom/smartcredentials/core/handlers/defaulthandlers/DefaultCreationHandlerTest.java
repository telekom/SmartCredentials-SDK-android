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

package de.telekom.smartcredentials.core.handlers.defaulthandlers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.core.domain.utils.MocksProvider;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultCreationHandlerTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private DefaultCreationHandler mDefaultCreationHandler;
    private EncryptionStrategy mEncryptionStrategy;

    @Before
    public void setUp() {
        mEncryptionStrategy = MocksProvider.provideEncryptionStrategy();
        mDefaultCreationHandler = new DefaultCreationHandler(mEncryptionStrategy);
    }

    @Test
    public void createPublicKeyCallsGetPublicKeyOnEncryptionStrategy() throws EncryptionException {
        String alias = "test";

        mDefaultCreationHandler.createPublicKey(alias);

        verify(mEncryptionStrategy, times(1)).getPublicKey(alias);
    }

    @Test
    public void createPublicKeyThrowsEncryptionException() throws EncryptionException {
        String alias = "test";
        String err = "err";
        when(mEncryptionStrategy.getPublicKey(alias)).thenThrow(new EncryptionException(err));

        thrown.expect(EncryptionException.class);
        thrown.expectMessage(err);

        mDefaultCreationHandler.createPublicKey(alias);
    }

    @Test
    public void encryptThrowsEncryptionException() throws EncryptionException {
        boolean isSensitive = false;
        String toEncrypt = "toEncrypt";
        String err = "err";
        when(mEncryptionStrategy.encrypt(toEncrypt, isSensitive, EncryptionAlgorithm.RSA_2048))
                .thenThrow(new EncryptionException(err));

        thrown.expect(EncryptionException.class);
        thrown.expectMessage(err);

        mDefaultCreationHandler.encrypt(toEncrypt, isSensitive, EncryptionAlgorithm.RSA_2048);
    }

    @Test
    public void encryptCallsMethodOnEncryptionStrategy() throws EncryptionException {
        boolean isSensitive = false;
        String toEncrypt = "toEncrypt";

        mDefaultCreationHandler.encrypt(toEncrypt, isSensitive, EncryptionAlgorithm.AES_256);

        verify(mEncryptionStrategy, times(1))
                .encrypt(toEncrypt, isSensitive, EncryptionAlgorithm.AES_256);
    }
}