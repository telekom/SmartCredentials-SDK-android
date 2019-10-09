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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static de.telekom.smartcredentials.core.ModelGenerator.getSuccessfulResponse;
import static de.telekom.smartcredentials.core.ModelGenerator.getUnsuccessfulResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SmartCredentialsResponseTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSuccessfulReturnsTrue() {
        SmartCredentialsResponse response = getSuccessfulResponse("some string");
        assertTrue(response.isSuccessful());
    }

    @Test
    public void isSuccessfulReturnsFalse() {
        SmartCredentialsResponse response = getUnsuccessfulResponse();
        assertFalse(response.isSuccessful());
    }

    @Test
    public void getDataReturnsData() {
        String data = "some string";
        SmartCredentialsResponse response = getSuccessfulResponse(data);
        assertEquals(response.getData(), data);
    }

    @Test
    public void getDataThrowsExceptionWhenResponseContainsError() {
        SmartCredentialsResponse response = getUnsuccessfulResponse();

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(SmartCredentialsResponse.GET_DATA_RESPONSE);
        response.getData();
    }

    @Test
    public void getErrorReturnsException() {
        SmartCredentialsResponse response = getUnsuccessfulResponse();
        assertTrue(response.getError() instanceof RuntimeException);
    }

    @Test
    public void getErrorThrowsExceptionWhenNoExceptionInResponse() {
        SmartCredentialsResponse response = getSuccessfulResponse("some string");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage(SmartCredentialsResponse.GET_ERROR_RESPONSE);
        response.getError();
    }

}