package com.operatortokenocb

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import timber.log.Timber

class SimChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (val intentAction = intent?.action) {
            "android.intent.action.SIM_STATE_CHANGED" -> checkWithExistingToken(context ?: error("wtf"))
            else -> error("$intentAction is not expected.")
        }
    }

    private fun checkWithExistingToken(context: Context) {
        val workRequest = OneTimeWorkRequestBuilder<TokenCheckingWork>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.METERED)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)

        Timber.tag("SCR").d("work scheduled")
    }
            "android.intent.action.SIM_STATE_CHANGED" -> TODO("trigger all the api check")
            else -> error("$intentAction is not expected.")
        }
    }
}