package com.operatortokenocb.data

class AlertRepository(
    private val api: AlertApi,
) {
    fun sendAlert(
        email: String,
        firstName: String?,
        lastName: String?,
        location: Location?,
    ) {
        TODO()
    }
}


data class Location(
    val latitude: Float,
    val longitude: Float,
    val accuracy: Float,
)

interface AlertApi {
    fun alert()
}