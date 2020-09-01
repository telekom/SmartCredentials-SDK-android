package de.telekom.camerademo;

import de.telekom.smartcredentials.core.logger.ApiLogger;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
class DemoLogger implements ApiLogger {

    @Override
    public void logError(String className, String errorMessage) {

    }

    @Override
    public void logMethodAccess(String className, String methodName) {

    }

    @Override
    public void logCallbackMethod(String className, String callbackClassName, String methodName) {

    }

    @Override
    public void logFactoryMethod(String factoryClassName, String factoryMethodName, String instantiatedClassName) {

    }

    @Override
    public void logEvent(String eventMessage) {

    }

    @Override
    public void logInfo(String infoMessage) {

    }

    @Override
    public void logExternal(String eventType, String eventName, String eventContent) {

    }
}
