package de.telekom.scqrlogindemo

import de.telekom.smartcredentials.core.logger.ApiLogger
import timber.log.Timber

class DemoLogger : ApiLogger {

    override fun logError(className: String?, errorMessage: String?) {
        Timber.tag(DemoApplication.TAG).d("error logged: %s", errorMessage)
    }

    override fun logMethodAccess(className: String?, methodName: String?) {
        Timber.tag(DemoApplication.TAG).d(
            "method %s called from class %s",
            methodName, className
        )
    }

    override fun logCallbackMethod(
        className: String?,
        callbackClassName: String?,
        methodName: String?
    ) {
        Timber.tag(DemoApplication.TAG).d(
            "callback %s called from class %s",
            methodName, callbackClassName
        )
    }

    override fun logFactoryMethod(
        factoryClassName: String?,
        factoryMethodName: String?,
        instantiatedClassName: String?
    ) {
        Timber.tag(DemoApplication.TAG).d(
            "factory method called %s from class %s",
            factoryMethodName, factoryClassName
        )
    }

    override fun logEvent(eventMessage: String?) {
        Timber.tag(DemoApplication.TAG).d("event logged: %s", eventMessage)
    }

    override fun logInfo(infoMessage: String?) {
        Timber.tag(DemoApplication.TAG).d("info logged: %s", infoMessage)
    }

    override fun logExternal(eventType: String?, eventName: String?, eventContent: String?) {
        Timber.tag(DemoApplication.TAG).d(
            "external event logged: %s - %s - %s",
            eventType, eventName, eventContent
        )
    }
}