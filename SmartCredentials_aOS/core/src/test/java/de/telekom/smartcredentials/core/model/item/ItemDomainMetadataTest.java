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

import de.telekom.smartcredentials.core.model.DomainModelException;

import static de.telekom.smartcredentials.core.model.item.ItemDomainMetadata.ITEM_TYPE;
import static de.telekom.smartcredentials.core.model.item.ItemDomainMetadata.KEY_SEPARATOR;
import static de.telekom.smartcredentials.core.model.item.ItemDomainMetadata.USER_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class ItemDomainMetadataTest {

    private ItemDomainMetadata mItemDomainMetadata;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        mItemDomainMetadata = new ItemDomainMetadata(true);
    }

    @Test
    public void getUniqueKeyPrefixThrowsExceptionWhenItemTypeIsEmpty() {
        PowerMockito.when(TextUtils.isEmpty(mItemDomainMetadata.getItemType())).thenReturn(true);

        thrown.expect(DomainModelException.class);
        thrown.expectMessage(ItemDomainMetadata.UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE);

        mItemDomainMetadata.getUniqueKeyPrefix();
    }

    @Test
    public void getUniqueKeyPrefixThrowsExceptionWhenUserIdIsEmpty() {
        PowerMockito.when(TextUtils.isEmpty(mItemDomainMetadata.getUserId())).thenReturn(false, true);

        thrown.expect(DomainModelException.class);
        thrown.expectMessage(ItemDomainMetadata.UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE);

        mItemDomainMetadata.getUniqueKeyPrefix();
    }

    @Test
    public void getUniqueKeyPrefixReturnsType() {
        PowerMockito.when(TextUtils.isEmpty(anyString())).thenReturn(false);
        String type = "voucher";
        String userId = "123";
        String expectedPrefix = ITEM_TYPE + type + KEY_SEPARATOR + USER_ID + userId;
        mItemDomainMetadata.setItemType(type);
        mItemDomainMetadata.setUserId(userId);

        String actualPrefix = mItemDomainMetadata.getUniqueKeyPrefix();

        assertEquals(actualPrefix, expectedPrefix);
    }
}