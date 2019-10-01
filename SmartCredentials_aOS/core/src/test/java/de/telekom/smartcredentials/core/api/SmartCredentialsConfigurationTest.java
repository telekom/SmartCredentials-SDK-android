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

package de.telekom.smartcredentials.core.api;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.logger.ApiLogger;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lucian Iacob on November 07, 2018.
 */
public class SmartCredentialsConfigurationTest {

    private Context mContext;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        mContext = Mockito.mock(Context.class);
    }

    @Test
    public void creatingScConfigurationWithNullUserIdThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(SmartCredentialsConfiguration.Builder.NULL_USER_ID);

        new SmartCredentialsConfiguration.Builder(mContext, null).build();
    }

    @Test
    public void creatingScConfigurationWithNullContextThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(SmartCredentialsConfiguration.Builder.NULL_CONTEXT);

        new SmartCredentialsConfiguration.Builder(null, "").build();
    }

    @Test
    public void creatingScConfigurationWithNullAliasThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(SmartCredentialsConfiguration.Builder.NULL_ALIAS);

        new SmartCredentialsConfiguration.Builder(mContext, "")
                .setAppAlias(null)
                .build();
    }

    @Test
    public void creatingScConfigSetsParamsAsProvided() {
        String userId = "123";
        String appAlias = "alias";
        ApiLogger logger = Mockito.mock(ApiLogger.class);

        SmartCredentialsConfiguration config = new SmartCredentialsConfiguration.Builder(mContext, userId)
                .setAppAlias(appAlias)
                .setLogger(logger)
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .build();

        assertEquals(userId, config.getUserId());
        assertEquals(appAlias, config.getAppAlias());
        assertFalse(config.isRootCheckerEnabled());
        assertEquals(logger, config.getLogger());
        assertEquals(mContext, config.getContext());
    }

    @Test
    public void creatingScConfigWithoutParamsSetsDefaultParams() {
        String userId = "123";

        SmartCredentialsConfiguration config = new SmartCredentialsConfiguration.Builder(mContext, userId)
                .build();

        assertNull(config.getLogger());
        assertEquals("", config.getAppAlias());
        assertTrue(config.isRootCheckerEnabled());
    }
}