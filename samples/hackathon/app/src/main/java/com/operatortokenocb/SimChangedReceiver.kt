package com.operatortokenocb

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SimChangedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (val intentAction = intent?.action) {
            "android.intent.action.SIM_STATE_CHANGED" -> TODO("trigger all the api check")
            else -> error("$intentAction is not expected.")
        }
    }
}