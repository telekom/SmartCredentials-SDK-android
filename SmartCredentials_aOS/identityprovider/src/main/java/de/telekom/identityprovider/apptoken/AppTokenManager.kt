package de.telekom.identityprovider.apptoken

import de.telekom.identityprovider.rest.RestController
import io.reactivex.schedulers.Schedulers

class AppTokenManager(baseUrl: String) {

    private val restController: RestController

    init {
        restController = RestController(baseUrl)
    }

    fun getAccessToken(credentials: String): String {
        return restController.getAccessToken(credentials)
            .map { string: String -> string }
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }

    fun getBearerToken(accessToken: String, clientId: String, packageName: String): String {
        return restController.getBearerToken(accessToken, clientId, packageName)
            .map { string: String -> string }
            .subscribeOn(Schedulers.io())
            .blockingFirst()
    }
}