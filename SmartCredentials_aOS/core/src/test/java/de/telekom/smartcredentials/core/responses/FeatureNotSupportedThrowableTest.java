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

package de.telekom.smartcredentials.core.responses;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FeatureNotSupportedThrowableTest {

    private static final String ERR_MESSAGE = "error";
    private FeatureNotSupportedThrowable mFeatureNotSupportedThrowable;

    @Before
    public void setUp() {
        mFeatureNotSupportedThrowable = new FeatureNotSupportedThrowable(ERR_MESSAGE);
    }

    @Test
    public void testCreation() {
        assertEquals(mFeatureNotSupportedThrowable.getMessage(), ERR_MESSAGE);
    }
}
