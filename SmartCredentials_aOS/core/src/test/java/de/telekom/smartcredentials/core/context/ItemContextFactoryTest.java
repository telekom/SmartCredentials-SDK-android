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

package de.telekom.smartcredentials.core.context;

import org.junit.Before;
import org.junit.Test;

import de.telekom.smartcredentials.core.model.item.ContentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ItemContextFactoryTest {

    private final String mType = "type";
    private ItemContext mEncryptedNonSensitiveContext;
    private ItemContext mEncryptedSensitiveContext;
    private ItemContext mNonEncryptedSensitiveContext;
    private ItemContext mNonEncryptedNonSensitiveContext;

    @Before
    public void setUp() {
        mEncryptedNonSensitiveContext = ItemContextFactory.createEncryptedNonSensitiveItemContext(mType);
        mEncryptedSensitiveContext = ItemContextFactory.createEncryptedSensitiveItemContext(mType);
        mNonEncryptedSensitiveContext = ItemContextFactory.createNonEncryptedSensitiveItemContext(mType);
        mNonEncryptedNonSensitiveContext = ItemContextFactory.createNonEncryptedNonSensitiveItemContext(mType);
    }

    @Test
    public void createEncryptedSensitiveItemContext() {
        assertTrue(mEncryptedSensitiveContext.isEncrypted());
        assertEquals(mEncryptedSensitiveContext.getItemType().getDesc(), mType);
        assertEquals(mEncryptedSensitiveContext.getItemType().getContentType(), ContentType.SENSITIVE);
    }

    @Test
    public void createNonEncryptedSensitiveItemContext() {
        assertFalse(mNonEncryptedSensitiveContext.isEncrypted());
        assertEquals(mNonEncryptedSensitiveContext.getItemType().getDesc(), mType);
        assertEquals(mNonEncryptedSensitiveContext.getItemType().getContentType(), ContentType.SENSITIVE);
    }

    @Test
    public void createEncryptedNonSensitiveItemContext() {
        assertTrue(mEncryptedNonSensitiveContext.isEncrypted());
        assertEquals(mEncryptedNonSensitiveContext.getItemType().getDesc(), mType);
        assertEquals(mEncryptedNonSensitiveContext.getItemType().getContentType(), ContentType.NON_SENSITIVE);
    }

    @Test
    public void createNonEncryptedNonSensitiveItemContext() {
        assertFalse(mNonEncryptedNonSensitiveContext.isEncrypted());
        assertEquals(mNonEncryptedNonSensitiveContext.getItemType().getDesc(), mType);
        assertEquals(mNonEncryptedNonSensitiveContext.getItemType().getContentType(), ContentType.NON_SENSITIVE);
    }

}