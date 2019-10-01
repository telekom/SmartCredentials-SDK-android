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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EnvelopeExceptionReasonTest {

    @Test
    public void mapMapsStringToEnum() {
        assertEquals(EnvelopeExceptionReason.UID_EXCEPTION_MESSAGE,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.UID_EXCEPTION_MESSAGE.getReason()));
        assertEquals(EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG.getReason()));
        assertEquals(EnvelopeExceptionReason.NO_DETAILS_EXCEPTION_MSG,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.NO_DETAILS_EXCEPTION_MSG.getReason()));
        assertEquals(EnvelopeExceptionReason.UID_EXCEPTION_MESSAGE,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.UID_EXCEPTION_MESSAGE.getReason()));
        assertEquals(EnvelopeExceptionReason.UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE.getReason()));
        assertEquals(EnvelopeExceptionReason.UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE.getReason()));
        assertEquals(EnvelopeExceptionReason.UNIQUE_KEY_EXCEPTION_MESSAGE,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.UNIQUE_KEY_EXCEPTION_MESSAGE.getReason()));
        assertEquals(EnvelopeExceptionReason.NULL_PARAMETER_EXCEPTION_MSG,
                EnvelopeExceptionReason.map(EnvelopeExceptionReason.NULL_PARAMETER_EXCEPTION_MSG.getReason()));
        assertNull(EnvelopeExceptionReason.map("some other string"));
        assertNull(EnvelopeExceptionReason.map(null));
    }

}