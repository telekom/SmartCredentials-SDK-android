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

package de.telekom.smartcredentials.core.domain.controllers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.core.controllers.CreationController;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.handlers.CreationHandler;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.domain.utils.MocksProvider;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreationControllerTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private CreationHandler mCreationHandler;
    private CreationController mCreationController;

    @Before
    public void setUp() {
        mCreationHandler = MocksProvider.provideCreationHandler();
        mCreationController = new CreationController(mCreationHandler);
    }

    @Test
    public void createPublicKeyCallsCreateOnCreationHandler() throws EncryptionException {
        String alias = "test";

        mCreationController.createPublicKey(alias);

        verify(mCreationHandler).createPublicKey(alias);
    }

    @Test
    public void createPublicKeyThrowsEncryptionExceptionWhenHandlerThrowsEncryptionException()
            throws EncryptionException {
        String alias = "test";
        String err = "err";
        when(mCreationHandler.createPublicKey(alias)).thenThrow(new EncryptionException(err));

        thrown.expect(EncryptionException.class);
        thrown.expectMessage(err);

        mCreationController.createPublicKey(alias);
    }

    @Test
    public void encryptThrowsEncryptionExceptionWhenHandlerThrowsEncryptionException()
            throws EncryptionException {
        String alias = "test";
        String err = "err";
        String toEncrypt = "toEncrypt";
        when(mCreationHandler.encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256))
                .thenThrow(new EncryptionException(err));

        thrown.expect(EncryptionException.class);
        thrown.expectMessage(err);

        mCreationController.encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256);
    }

    @Test
    public void encryptCallsMethodOnCreationHandler() throws EncryptionException {
        String alias = "test";
        String toEncrypt = "toEncrypt";

        mCreationController.encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);

        verify(mCreationHandler, times(1))
                .encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
    }
}