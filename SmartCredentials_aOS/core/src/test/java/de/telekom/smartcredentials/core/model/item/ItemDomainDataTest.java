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

package de.telekom.smartcredentials.core.model.item;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.core.domain.utils.MocksProvider;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class ItemDomainDataTest {

    private ItemDomainData mItemDomainData;
    private EncryptionStrategy mEncryptionStrategy;
    private String mIdentifier;
    private String mPrivateData;
    private String mAlias;

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();

    @Before
    public void setUp() {
        mEncryptionStrategy = MocksProvider.provideEncryptionStrategy();
        mItemDomainData = new ItemDomainData();

        mIdentifier = "identifier";
        mPrivateData = "private data";
        mItemDomainData.setIdentifier(mIdentifier)
                .setPrivateData(mPrivateData);
        mAlias = "alias";
    }

    @Test
    public void encryptWithEncryptsBothIdentifierAndPrivateData() throws EncryptionException {
        mItemDomainData.encryptWith(mEncryptionStrategy, mAlias);

        verify(mEncryptionStrategy).encrypt(mIdentifier, mAlias);
        verify(mEncryptionStrategy).encrypt(mPrivateData, mAlias);
    }

    @Test
    public void encryptThrowsEncryptionExceptionWhenEncryptingIdentifierFailed() throws EncryptionException {
        String expectedMessage = "could not encrypt text";
        when(mEncryptionStrategy.encrypt(mIdentifier, mAlias)).thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.encryptWith(mEncryptionStrategy, mAlias);
    }

    @Test
    public void encryptThrowsEncryptionExceptionWhenEncryptingPrivateDataFailed() throws EncryptionException {
        String expectedMessage = "could not encrypt text";
        when(mEncryptionStrategy.encrypt(mPrivateData, mAlias)).thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.encryptWith(mEncryptionStrategy, mAlias);
    }

    @Test
    public void decryptWithDecryptsBothIdentifierAndPrivateData() throws EncryptionException {
        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias);

        verify(mEncryptionStrategy).decrypt(mIdentifier, mAlias);
        verify(mEncryptionStrategy).decrypt(mPrivateData, mAlias);
    }

    @Test
    public void decryptThrowsEncryptionExceptionWhenDecryptingIdentifierFailed() throws EncryptionException {
        String expectedMessage = "could not decrypt text";
        when(mEncryptionStrategy.decrypt(mIdentifier, mAlias)).thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias);
    }

    @Test
    public void decryptThrowsEncryptionExceptionWhenDecryptingPrivateDataFailed() throws EncryptionException {
        String expectedMessage = "could not decrypt text";
        when(mEncryptionStrategy.decrypt(mPrivateData, mAlias)).thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias);
    }

    @Test
    public void decryptWithThrowsEncryptionExceptionWhenDecryptingIdentifierFailed() throws EncryptionException {
        String expectedMessage = "could not decrypt text";

        when(mEncryptionStrategy.decrypt(mIdentifier, mAlias, EncryptionAlgorithm.AES_256))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias, EncryptionAlgorithm.AES_256);
    }

    @Test
    public void decryptWithThrowsEncryptionExceptionWhenDecryptingPrivateDataFailed() throws EncryptionException {
        String expectedMessage = "could not decrypt text";

        when(mEncryptionStrategy.decrypt(mPrivateData, mAlias, EncryptionAlgorithm.RSA_2048))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias, EncryptionAlgorithm.RSA_2048);
    }

    @Test
    public void decryptWithCallsDecryptOnEncryptionStrategy() throws EncryptionException {
        mItemDomainData.decryptWith(mEncryptionStrategy, mAlias, EncryptionAlgorithm.DEFAULT);

        verify(mEncryptionStrategy, times(1))
                .decrypt(mIdentifier, mAlias, EncryptionAlgorithm.DEFAULT);
        verify(mEncryptionStrategy, times(1))
                .decrypt(mPrivateData, mAlias, EncryptionAlgorithm.DEFAULT);
    }

    @Test
    public void partiallyEncryptWithCallsMethodOnEncryptionStrategy() throws EncryptionException {
        mItemDomainData.partiallyEncryptWith(mEncryptionStrategy, mAlias);

        verify(mEncryptionStrategy, times(1))
                .encrypt(mIdentifier, mAlias);
        verify(mEncryptionStrategy, never())
                .encrypt(eq(mPrivateData), anyString());
    }

    @Test
    public void partiallyEncryptWithThrowsEncryptionExceptionWhenEncryptingIdentifierFailed()
            throws EncryptionException {
        String expectedMessage = "could not decrypt text";

        when(mEncryptionStrategy.encrypt(mIdentifier, mAlias))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainData.partiallyEncryptWith(mEncryptionStrategy, mAlias);
    }
}