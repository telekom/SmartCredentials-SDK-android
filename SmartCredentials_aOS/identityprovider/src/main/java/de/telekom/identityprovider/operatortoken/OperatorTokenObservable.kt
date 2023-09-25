package de.telekom.identityprovider.operatortoken

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe
import java.util.concurrent.Executors

class OperatorTokenObservable(
    private val context: Context,
    private val intent: Intent,
    private val bearerToken: String,
    private val clientId: String,
    private val scope: String
) :
    SingleOnSubscribe<SmartCredentialsResponse<String>> {

    private fun bindService(serviceConnection: ServiceConnection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.bindService(
                intent,
                Context.BIND_AUTO_CREATE,
                Executors.newSingleThreadExecutor(),
                serviceConnection
            )
        } else {
            context.bindService(
                intent,
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
    }

    private fun unbindService(serviceConnection: ServiceConnection) {
        context.unbindService(serviceConnection)
    }

    override fun subscribe(emitter: SingleEmitter<SmartCredentialsResponse<String>>) {
        val serviceConnection = OperatorTokenServiceConnection(
            bearerToken,
            clientId,
            scope,
            emitter
        )
        bindService(serviceConnection)
        emitter.setCancellable {
            unbindService(serviceConnection)
        }
    }
}