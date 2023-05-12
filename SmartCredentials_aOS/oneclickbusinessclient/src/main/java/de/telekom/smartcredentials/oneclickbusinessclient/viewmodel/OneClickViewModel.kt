/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.telekom.smartcredentials.oneclickbusinessclient.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.telekom.smartcredentials.core.identityprovider.IdentityProviderCallback
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse
import de.telekom.smartcredentials.oneclickbusinessclient.operatortoken.OperatorTokenManager
import de.telekom.smartcredentials.oneclickbusinessclient.operatortoken.TokenResponse
import de.telekom.smartcredentials.oneclickbusinessclient.operatortoken.TokenRetrieveState
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
class OneClickViewModel(
    private val operatorTokenManager: OperatorTokenManager
) : ViewModel(), IdentityProviderCallback {

    companion object {
        private const val DEFAULT_DELAY: Long = 500
        private const val FINISH_DELAY: Long = 2000
    }

    val loadingDialogState = mutableStateOf(LoadingDialogState())
    val showErrorDialogState = mutableStateOf(false)
    val tokenResponse = mutableStateOf(TokenResponse(TokenRetrieveState.NOT_RETRIEVED, ""))
    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    fun startFlow() =
        viewModelScope.launch(job) {
            delay(DEFAULT_DELAY)

            loadingDialogState.value = LoadingDialogState(
                retrieveInfoInProgress = true,
                retrieveInfoDone = false,
                openPortalDone = false
            )
            operatorTokenManager.requestOperatorToken(this@OneClickViewModel)
        }

    private fun onSuccessfulFetch(token: String) = viewModelScope.launch(job) {
        delay(FINISH_DELAY)
        loadingDialogState.value = LoadingDialogState(
            retrieveInfoInProgress = true,
            retrieveInfoDone = true,
            openPortalDone = false
        )
        ApiLoggerResolver.logEvent("token: $token")

        delay(FINISH_DELAY)
        onLoadingFinished(
            TokenResponse(
                TokenRetrieveState.SUCCESSFUL,
                token
            )
        )
    }

    private fun handleUnsuccessfulFetch() = viewModelScope.launch(job) {
        delay(FINISH_DELAY)
        loadingDialogState.value = LoadingDialogState(
            retrieveInfoInProgress = true,
            retrieveInfoDone = true,
            openPortalDone = false
        )
        ApiLoggerResolver.logEvent("Unsuccessful token fetch")

        delay(FINISH_DELAY)

        onLoadingFinished(
            TokenResponse(
                TokenRetrieveState.UNSUCCESSFUL,
                ""
            )
        )
    }

    private fun onLoadingFinished(response: TokenResponse) = viewModelScope.launch(job) {
        delay(DEFAULT_DELAY)

        loadingDialogState.value = LoadingDialogState(
            retrieveInfoInProgress = true,
            retrieveInfoDone = true,
            openPortalDone = true
        )
        if (response.state == TokenRetrieveState.UNSUCCESSFUL) {
            showErrorDialogState.value = true
        } else {
            tokenResponse.value = response
        }
        resetState()
    }

    fun stopComputation() {
        job.cancelChildren()
        showErrorDialogState.value = false
        tokenResponse.value = TokenResponse(TokenRetrieveState.NOT_RETRIEVED, "")
        resetState()
    }

    private fun resetState() {
        loadingDialogState.value = LoadingDialogState()
    }

    override fun onResult(result: SmartCredentialsApiResponse<String>) {
        if (result.isSuccessful) {
            onSuccessfulFetch(result.data)
        } else {
            handleUnsuccessfulFetch()
        }
    }
}