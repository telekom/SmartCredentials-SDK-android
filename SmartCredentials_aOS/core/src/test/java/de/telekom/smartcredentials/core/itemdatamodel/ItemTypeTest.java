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

package de.telekom.smartcredentials.core.itemdatamodel;

import org.junit.Before;
import org.junit.Test;

import de.telekom.smartcredentials.core.model.item.ContentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemTypeTest {

    private static final String TYPE_GENERAL = "VOUCHER";
    private static final String TYPE_TOKEN = "TOKEN";

    private ItemType mGeneralItemType;
    private ItemType mUserItemType;

    @Before
    public void setUp() {
        mGeneralItemType = new ItemType(TYPE_GENERAL, ContentType.NON_SENSITIVE);
        mUserItemType = new ItemType(TYPE_TOKEN, ContentType.SENSITIVE);
    }

    @Test
    public void testGetContentType() {
        assertEquals(ContentType.NON_SENSITIVE, mGeneralItemType.getContentType());
        assertEquals(ContentType.SENSITIVE, mUserItemType.getContentType());
    }

    @Test
    public void testGetDescription() {
        assertEquals(mGeneralItemType.getDesc(), TYPE_GENERAL);
        assertEquals(mUserItemType.getDesc(), TYPE_TOKEN);
    }

    @Test
    public void testIsSensitive() {
        assertFalse(mGeneralItemType.isSensitive());
        assertTrue(mUserItemType.isSensitive());
        assertFalse(new ItemType(TYPE_TOKEN, null).isSensitive());
    }

}