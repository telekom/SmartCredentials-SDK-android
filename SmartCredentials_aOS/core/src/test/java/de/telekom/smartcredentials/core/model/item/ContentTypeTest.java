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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ContentTypeTest {
    @Test
    public void isSensitiveReturnsTrue() {
        assertTrue(ContentType.SENSITIVE.isSensitive());
    }

    @Test
    public void isSensitiveReturnsFalse() {
        assertFalse(ContentType.NON_SENSITIVE.isSensitive());
    }

    @Test
    public void valuesReturnsAnArrayWithTwoElements() {
        assertEquals(ContentType.values().length, 2);
    }

    @Test
    public void valueOfTest() {
        assertEquals(ContentType.valueOf("NON_SENSITIVE"), ContentType.NON_SENSITIVE);
        assertEquals(ContentType.valueOf("SENSITIVE"), ContentType.SENSITIVE);
    }

}