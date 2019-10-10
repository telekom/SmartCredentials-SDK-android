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

package de.telekom.smartcredentials.networking.request.models;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.networking.request.models.enums.RequestFailure;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;

import static de.telekom.smartcredentials.networking.request.models.FailedRequest.TYPE_MISMATCHED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FailedRequestTest {

    @Rule
    public final ExpectedException mExceptionRule = ExpectedException.none();

    @Test
    public void newInstanceThrowsExceptionWhenFailureTypeObjectAndDetailsObjectDoNotMatch1() {
        mExceptionRule.expect(RuntimeException.class);
        mExceptionRule.expectMessage(String.format(TYPE_MISMATCHED, RequestFailureLevel.REQUEST.name(),
                SocketFactoryFailure.MISSING_PERMISSION.getClass().getSimpleName()));
        FailedRequest.newInstance(RequestFailureLevel.REQUEST, SocketFactoryFailure.MISSING_PERMISSION);
    }

    @Test
    public void newInstanceThrowsExceptionWhenFailureTypeObjectAndDetailsObjectDoNotMatch2() {
        mExceptionRule.expect(RuntimeException.class);
        mExceptionRule.expectMessage(String.format(TYPE_MISMATCHED, RequestFailureLevel.SOCKET_CREATION.name(),
                RequestFailure.NULL_PARSED_URL.getClass().getSimpleName()));
        FailedRequest.newInstance(RequestFailureLevel.SOCKET_CREATION, RequestFailure.NULL_PARSED_URL);
    }

    @Test
    public void newInstanceCreatesFailedRequestInstanceWithNullMessage() {
        FailedRequest request = FailedRequest.newInstance(RequestFailureLevel.SOCKET_CREATION,
                SocketFactoryFailure.MISSING_PERMISSION);

        assertEquals(request.getRequestFailureLevel(), RequestFailureLevel.SOCKET_CREATION);
        assertEquals(request.getDetails(), SocketFactoryFailure.MISSING_PERMISSION);
        assertNull(request.getMessage());
    }

    @Test
    public void newInstanceCreatesFailedRequestInstanceWithNonNullMessage() {
        String message = "Failed";
        FailedRequest request = FailedRequest.newInstance(RequestFailureLevel.SOCKET_CREATION,
                SocketFactoryFailure.MISSING_PERMISSION, message);

        assertEquals(request.getRequestFailureLevel(), RequestFailureLevel.SOCKET_CREATION);
        assertEquals(request.getDetails(), SocketFactoryFailure.MISSING_PERMISSION);
        assertEquals(request.getMessage(), message);
    }
}