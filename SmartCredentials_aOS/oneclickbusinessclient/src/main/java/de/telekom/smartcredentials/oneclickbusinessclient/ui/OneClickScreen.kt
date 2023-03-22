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
package de.telekom.smartcredentials.oneclickbusinessclient.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController
import de.telekom.smartcredentials.oneclickbusinessclient.identityProvider.TokenResponse
import de.telekom.smartcredentials.oneclickbusinessclient.identityProvider.TokenRetrieveState
import de.telekom.smartcredentials.oneclickbusinessclient.ui.dialogs.LoadingDialogFragment
import de.telekom.smartcredentials.oneclickbusinessclient.ui.dialogs.OneClickDialogFragment
import de.telekom.smartcredentials.oneclickbusinessclient.ui.dialogs.RetrieveErrorDialogFragment
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModel
import de.telekom.smartcredentials.oneclickbusinessclient.viewmodel.OneClickViewModelFactory

/**
 * Created by larisa-maria.suciu@endava.com at 22/03/2023
 */
@Composable
fun OneClickScreen(
    viewModel: OneClickViewModel,
    showComposeFlow: Boolean,
    signalFlowEnd: () -> Unit,
    signalTokenReceived: (TokenResponse) -> Unit
) {
    if (showComposeFlow) {

        val loadingDialogState = remember { viewModel.loadingDialogState }
        val openErrorDialogFragment = remember { viewModel.showErrorDialogState }
        val openOneClickDialogFragment = remember { mutableStateOf(false) }
        val openLoadingDialogFragment = remember { mutableStateOf(false) }
        val tokenResponse = remember { mutableStateOf(viewModel.tokenResponse.value) }

        if (!openErrorDialogFragment.value && !openLoadingDialogFragment.value) {
            openOneClickDialogFragment.value = true
        }

        OneClickDialogFragment(
            openOneClickDialogFragment.value,
            onPositiveButtonClicked = {
                openOneClickDialogFragment.value = false
                openLoadingDialogFragment.value = true
                viewModel.startFlow()
            },
            onDismissRequest = {
                signalFlowEnd()
                openOneClickDialogFragment.value = false
            }
        )


        LoadingDialogFragment(
            openDialog = openLoadingDialogFragment.value,
            retrieveInfoInProgress = loadingDialogState.value.retrieveInfoInProgress,
            retrieveInfoDone = loadingDialogState.value.retrieveInfoDone,
            openPortalDone = loadingDialogState.value.openPortalDone,
            onDismiss = {
                openLoadingDialogFragment.value = false
                viewModel.stopComputation()
                signalFlowEnd()
            }
        )

        if (openErrorDialogFragment.value) {
            openLoadingDialogFragment.value = false
        }
        RetrieveErrorDialogFragment(
            showDialog = openErrorDialogFragment.value,
            onDismissRequest = {
                openErrorDialogFragment.value = false
                signalFlowEnd()
            })

        if (tokenResponse.value.state == TokenRetrieveState.SUCCESSFUL) {
            signalTokenReceived(tokenResponse.value)
        }
    }
}