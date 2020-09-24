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

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.DomainModelException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.core.domain.utils.MocksProvider;

import static de.telekom.smartcredentials.core.model.item.ItemDomainMetadata.KEY_SEPARATOR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class ItemDomainModelTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private ItemDomainModel mItemDomainModel;
    private ItemDomainMetadata mItemDomainMetadata;
    private ItemDomainData mItemDomainData;
    private EncryptionStrategy mEncryptionStrategy;

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);

        mEncryptionStrategy = MocksProvider.provideEncryptionStrategy();
        mItemDomainMetadata = MocksProvider.provideItemDomainMetadata();
        mItemDomainData = MocksProvider.provideItemDomainData();

        mItemDomainModel = new ItemDomainModel();
        mItemDomainModel
                .setMetadata(mItemDomainMetadata)
                .setData(mItemDomainData);
    }

    @Test
    public void encryptData() throws EncryptionException {
        mItemDomainModel.encryptData(mEncryptionStrategy, false);

        verify(mItemDomainData).encryptWith(mEncryptionStrategy, false);
    }

    @Test
    public void encryptDataThrowsEncryptionException() throws EncryptionException {
        String expectedMessage = "could not encrypt text";
        doThrow(new EncryptionException(expectedMessage))
                .when(mItemDomainData).encryptWith(mEncryptionStrategy, false);

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainModel.encryptData(mEncryptionStrategy, false);
    }

    @Test
    public void decryptData() throws EncryptionException {
        mItemDomainModel.decryptData(mEncryptionStrategy, EncryptionAlgorithm.AES_256);

        verify(mItemDomainData).decryptWith(mEncryptionStrategy, EncryptionAlgorithm.AES_256);
    }

    @Test
    public void decryptDataThrowsEncryptionException() throws EncryptionException {
        String expectedMessage = "could not decrypt text";
        doThrow(new EncryptionException(expectedMessage))
                .when(mItemDomainData).decryptWith(mEncryptionStrategy, EncryptionAlgorithm.AES_256);

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainModel.decryptData(mEncryptionStrategy, EncryptionAlgorithm.AES_256);
    }

    @Test
    public void getUniqueKeyThrowsExceptionWhenUidIsNull() {
        PowerMockito.when(TextUtils.isEmpty(mItemDomainModel.getUid())).thenReturn(true);

        thrown.expect(DomainModelException.class);
        thrown.expectMessage(ItemDomainModel.UNIQUE_KEY_EXCEPTION_MESSAGE);

        mItemDomainModel.getUniqueKey();
    }

    @Test
    public void getUniqueKeyReturnsComputedKey() {
        String prefix = "prefix";
        String id = "1";
        String expectedKey = prefix + KEY_SEPARATOR + id;
        PowerMockito.when(TextUtils.isEmpty(anyString())).thenReturn(false);
        when(mItemDomainMetadata.getUniqueKeyPrefix()).thenReturn(prefix);
        mItemDomainModel.setId(id);

        String uniqueKey = mItemDomainModel.getUniqueKey();

        assertEquals(expectedKey, uniqueKey);
    }

    @Test
    public void partiallyEncryptData() throws EncryptionException {
        mItemDomainModel.partiallyEncrypt(mEncryptionStrategy, false);

        verify(mItemDomainData, times(1))
                .partiallyEncryptWith(mEncryptionStrategy, false);
    }

    @Test
    public void partiallyEncryptDataThrowsEncryptionException() throws EncryptionException {
        String expectedMessage = "could not decrypt text";
        doThrow(new EncryptionException(expectedMessage))
                .when(mItemDomainData).partiallyEncryptWith(mEncryptionStrategy, false);

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainModel.partiallyEncrypt(mEncryptionStrategy, false);
    }

    @Test
    public void decryptDataWithAlgorithmCallsMethodOnDomainData() throws EncryptionException {
        mItemDomainModel.decryptData(mEncryptionStrategy, EncryptionAlgorithm.RSA_2048);

        verify(mItemDomainData, times(1))
                .decryptWith(mEncryptionStrategy, EncryptionAlgorithm.RSA_2048);
    }

    @Test
    public void decryptDataWithAlgorithmThrowsEncryptionException() throws EncryptionException {
        String expectedMessage = "could not decrypt text";
        doThrow(new EncryptionException(expectedMessage))
                .when(mItemDomainData).decryptWith(mEncryptionStrategy, EncryptionAlgorithm.AES_256);

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mItemDomainModel.decryptData(mEncryptionStrategy, EncryptionAlgorithm.AES_256);
    }
}