package de.telekom.scpushnotificationsdemo;

import de.telekom.smartcredentials.core.logger.ApiLogger;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 8/26/2020
 */
public class DemoLogger implements ApiLogger {

    @Override
    public void logError(String className, String errorMessage) {
        Timber.tag(DemoApplication.TAG).d("error logged: %s", errorMessage);
    }

    @Override
    public void logMethodAccess(String className, String methodName) {
        Timber.tag(DemoApplication.TAG).d("method %s called from class %s",
                methodName, className);
    }

    @Override
    public void logCallbackMethod(String className, String callbackClassName, String methodName) {
        Timber.tag(DemoApplication.TAG).d("callback %s called from class %s",
                methodName, callbackClassName);
    }

    @Override
    public void logFactoryMethod(String factoryClassName, String factoryMethodName, String instantiatedClassName) {
        Timber.tag(DemoApplication.TAG).d("factory method called % s from class %s",
                factoryMethodName, factoryClassName);
    }

    @Override
    public void logEvent(String eventMessage) {
        Timber.tag(DemoApplication.TAG).d("event logged: %s", eventMessage);
    }

    @Override
    public void logInfo(String infoMessage) {
        Timber.tag(DemoApplication.TAG).d("info logged: %s", infoMessage);
    }

    @Override
    public void logExternal(String eventType, String eventName, String eventContent) {
        Timber.tag(DemoApplication.TAG).d("external event logged: %s - %s - %s",
                eventType, eventName, eventContent);
    }
}