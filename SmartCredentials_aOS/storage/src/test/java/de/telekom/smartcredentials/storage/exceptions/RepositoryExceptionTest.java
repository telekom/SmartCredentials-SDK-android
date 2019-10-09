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

package de.telekom.smartcredentials.storage.exceptions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RepositoryExceptionTest {

    private static final String EXCEPTION_MESSAGE = "i'm a message";

    private Throwable mThrowable;
    private RepositoryException mRepositoryExceptionWithThrowable;
    private RepositoryException mRepositoryExceptionWithMessageAndThrowable;

    @Before
    public void setup() {
        mThrowable = new Throwable();
        mRepositoryExceptionWithThrowable = new RepositoryException(mThrowable);
        mRepositoryExceptionWithMessageAndThrowable = new RepositoryException(EXCEPTION_MESSAGE, mThrowable);
    }

    @Test
    public void testCreateRepoExceptionWithThrowable() {
        assertThat(mRepositoryExceptionWithThrowable.getCause(), is(mThrowable));
    }

    @Test
    public void testCreateRepoExceptionWithMessageAndThrowable() {
        assertThat(mRepositoryExceptionWithMessageAndThrowable.getCause(), is(mThrowable));
        assertThat(mRepositoryExceptionWithMessageAndThrowable.getMessage(), is(EXCEPTION_MESSAGE));
    }

}