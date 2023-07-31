package de.telekom.identityprovider.operatortoken

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import de.telekom.identityprovider.exception.ServiceConnectionException
import de.telekom.smartcredentials.core.IOperatorTokenCallback
import de.telekom.smartcredentials.core.IOperatorTokenService
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse
import io.reactivex.SingleEmitter

class OperatorTokenServiceConnection(
    private val bearerToken: String,
    private val clientId: String,
    private val scope: String,
    private val emitter: SingleEmitter<SmartCredentialsResponse<String>>
) :
    ServiceConnection {

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        try {
            val operatorTokenService: IOperatorTokenService =
                IOperatorTokenService.Stub.asInterface(service)
            operatorTokenService.getOperatorToken(
                bearerToken, clientId, scope, OperatorTokenCallback()
            )
        } catch (e: Exception) {
            emitter.onSuccess(SmartCredentialsResponse(ServiceConnectionException(e)))
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        if(!emitter.isDisposed) {
            emitter.onError(ServiceConnectionException("Service connection lost"))
        }
    }

    inner class OperatorTokenCallback() :
        IOperatorTokenCallback.Stub() {

        override fun onOperatorTokenReceived(
            isSuccess: Boolean,
            operatorToken: String,
            errorMessage: String
        ) {
            emitter.onSuccess(
                if (isSuccess) {
                    SmartCredentialsResponse(operatorToken)
                } else {
                    SmartCredentialsResponse(Exception(errorMessage))
                }
            )
        }
    }
}