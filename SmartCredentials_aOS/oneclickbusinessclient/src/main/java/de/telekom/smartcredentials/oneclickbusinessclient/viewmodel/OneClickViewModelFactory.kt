package de.telekom.smartcredentials.oneclickbusinessclient.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController

class OneClickViewModelFactory(
    private val oneClickBusinessClientController: OneClickBusinessClientController
):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        OneClickViewModel(oneClickBusinessClientController) as T
}