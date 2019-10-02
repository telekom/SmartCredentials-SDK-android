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

public class ApiLoggerResolver {

    static ApiLogger mApiLogger;

    private ApiLoggerResolver() {
        // required empty constructor
    }

    public static void setApiLogger(ApiLogger apiLogger) {
        if (mApiLogger == null) {
            mApiLogger = apiLogger;
        }
    }

    public static void logError(String className, String errorMessage) {
        if (mApiLogger != null) {
            mApiLogger.logError(className, errorMessage);
        }
    }

    public static void logMethodAccess(String className, String methodName) {
        if (mApiLogger != null) {
            mApiLogger.logMethodAccess(className, methodName);
        }
    }

    public static void logCallbackMethod(String className, String callbackClassName, String methodName) {
        if (mApiLogger != null) {
            mApiLogger.logCallbackMethod(className, callbackClassName, methodName);
        }
    }

    public static void logFactoryMethod(String factoryClassName, String factoryMethodName, String instantiatedClassName) {
        if (mApiLogger != null) {
            mApiLogger.logFactoryMethod(factoryClassName, factoryMethodName, instantiatedClassName);
        }
    }

    public static void logEvent(String eventMessage) {
        if (mApiLogger != null) {
            mApiLogger.logEvent(eventMessage);
        }
    }

    public static void logInfo(String infoMessage) {
        if (mApiLogger != null) {
            mApiLogger.logInfo(infoMessage);
        }
    }

    public static void logExternal(String eventType, String eventName, String eventContent) {
        if (mApiLogger != null) {
            mApiLogger.logExternal(eventType, eventName, eventContent);
        }
    }
}
