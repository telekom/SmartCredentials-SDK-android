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

package de.telekom.smartcredentials.security.otp;

import org.junit.Test;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.security.Base32String;

import static org.junit.Assert.*;

public class Base32StringTest {

    @Test
    public void stringIsTheSameAfterDecodingAndEncodingBack() throws Base32String.DecodingException, EncryptionException {
        String encodedString = "K4ZU4OLRIFZWUZLKKY4DIRTEMFCUCMSN";
        byte[] decoded = Base32String.decode(encodedString);

        assertEquals(encodedString, Base32String.encode(decoded));
    }

}