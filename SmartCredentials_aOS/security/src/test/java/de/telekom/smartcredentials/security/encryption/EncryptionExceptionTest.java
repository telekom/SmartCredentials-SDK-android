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

package de.telekom.smartcredentials.security.encryption;

import org.junit.Test;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

import static de.telekom.smartcredentials.core.security.EncryptionError.DECRYPTION_EXCEPTION_TEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EncryptionExceptionTest {

    @Test
    public void testCreateExceptionWithCause() {
        EncryptionException exception = new EncryptionException(new IllegalAccessException());
        assertTrue(exception.getCause() instanceof IllegalAccessException);
    }

    @Test
    public void testCreateExceptionWithCauseAndMessage() {
        EncryptionException exception = new EncryptionException(DECRYPTION_EXCEPTION_TEXT, new ArithmeticException());
        assertEquals(exception.getMessage(), DECRYPTION_EXCEPTION_TEXT);
        assertTrue(exception.getCause() instanceof ArithmeticException);
    }

    @Test
    public void testCreateExceptionWithMessage() {
        EncryptionException exception = new EncryptionException(DECRYPTION_EXCEPTION_TEXT);
        assertEquals(exception.getMessage(), DECRYPTION_EXCEPTION_TEXT);
    }

}