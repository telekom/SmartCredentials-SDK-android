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

public interface ApiLogger {

    void logError(String className, String errorMessage);

    void logMethodAccess(String className, String methodName);

    void logCallbackMethod(String className, String callbackClassName, String methodName);

    void logFactoryMethod(String factoryClassName, String factoryMethodName, String instantiatedClassName);

    void logEvent(String eventMessage);

    void logInfo(String infoMessage);

    void logExternal(String eventType, String eventName, String eventContent);
}
