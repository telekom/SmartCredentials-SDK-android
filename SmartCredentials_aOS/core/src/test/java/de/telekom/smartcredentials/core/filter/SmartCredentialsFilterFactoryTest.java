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

package de.telekom.smartcredentials.core.filter;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.model.item.ContentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class SmartCredentialsFilterFactoryTest {

    @Before
    public void setup() {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void createNonSensitiveItemFilterCreatesFilterForTypeWithoutId() {
        String type = "voucher";
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(type);

        assertNotNull(filter.getItemType());
        assertEquals(filter.getItemType().getContentType(), ContentType.NON_SENSITIVE);
        assertEquals(filter.getItemType().getDesc(), type);
        assertNull(filter.getId());
    }

    @Test
    public void createNonSensitiveItemFilterCreatesFilterForTypeAndId() {
        String expectedId = "5632";
        String type = "voucher";

        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createNonSensitiveItemFilter(expectedId, type);

        assertNotNull(filter.getItemType());
        assertEquals(filter.getItemType().getContentType(), ContentType.NON_SENSITIVE);
        assertEquals(filter.getItemType().getDesc(), type);
        assertEquals(filter.getId(), expectedId);
    }

    @Test
    public void createSensitiveItemFilterCreatesFilterForTypeWithoutId() {
        String type = "voucher";
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(type);

        assertNotNull(filter.getItemType());
        assertEquals(filter.getItemType().getContentType(), ContentType.SENSITIVE);
        assertEquals(filter.getItemType().getDesc(), type);
        assertNull(filter.getId());
    }

    @Test
    public void createSensitiveItemFilterCreatesFilterForTypeAndId() {
        String expectedId = "5632";
        String type = "voucher";

        SmartCredentialsFilter filter = SmartCredentialsFilterFactory.createSensitiveItemFilter(expectedId, type);

        assertNotNull(filter.getItemType());
        assertEquals(filter.getItemType().getContentType(), ContentType.SENSITIVE);
        assertEquals(filter.getItemType().getDesc(), type);
        assertEquals(filter.getId(), expectedId);
    }

}