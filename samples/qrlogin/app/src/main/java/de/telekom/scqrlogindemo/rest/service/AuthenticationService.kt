package de.telekom.scqrlogindemo.rest.service

import com.google.gson.JsonObject
import de.telekom.scqrlogindemo.rest.model.SignInRequestResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST(".")
    fun signIn(@Body requestBody: JsonObject): Observable<Response<SignInRequestResponse>>
}