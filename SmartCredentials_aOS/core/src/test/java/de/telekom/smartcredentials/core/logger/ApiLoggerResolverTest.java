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

package de.telekom.smartcredentials.core.logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class ApiLoggerResolverTest {

    private static final String TAG = "ApiLoggerResolverTest";

    @Before
    public void setUp() {
        ApiLogger apiLogger = Mockito.mock(ApiLogger.class);
        ApiLoggerResolver.setApiLogger(apiLogger);
    }

    @Test
    public void logErrorCallsHomonymousMethodOnApiLogger() {
        String error = "error";

        ApiLoggerResolver.logError(TAG, error);
        verify(ApiLoggerResolver.mApiLogger).logError(TAG, error);
    }

    @Test
    public void logMethodAccessCallsHomonymousMethodOnApiLogger() {
        String methodName = "onMethodLogged";

        ApiLoggerResolver.logMethodAccess(TAG, methodName);
        verify(ApiLoggerResolver.mApiLogger).logMethodAccess(TAG, methodName);
    }

    @Test
    public void logCallbackMethodCallsHomonymousMethodOnApiLogger() {
        String methodName = "onMethodLogged";
        String someClassName = "someClass";

        ApiLoggerResolver.logCallbackMethod(TAG, someClassName, methodName);
        verify(ApiLoggerResolver.mApiLogger).logCallbackMethod(TAG, someClassName, methodName);
    }

    @Test
    public void logFactoryMethodCallsHomonymousMethodOnApiLogger() {
        String factoryClassName = "factoryClassName";
        String factoryMethodName = "factoryMethodName";
        String instantiatedClassName = "instantiatedClassName";

        ApiLoggerResolver.logFactoryMethod(factoryClassName, factoryMethodName, instantiatedClassName);
        verify(ApiLoggerResolver.mApiLogger).logFactoryMethod(factoryClassName,
                factoryMethodName, instantiatedClassName);
    }

    @Test
    public void logEventCallsHomonymousMethodOnApiLogger() {
        String eventMessage = "someEvent";

        ApiLoggerResolver.logEvent(eventMessage);
        verify(ApiLoggerResolver.mApiLogger).logEvent(eventMessage);
    }

    @Test
    public void logInfoCallsHomonymousMethodOnApiLogger() {
        String infoMessage = "infoMessage";

        ApiLoggerResolver.logInfo(infoMessage);
        verify(ApiLoggerResolver.mApiLogger).logInfo(infoMessage);
    }

}