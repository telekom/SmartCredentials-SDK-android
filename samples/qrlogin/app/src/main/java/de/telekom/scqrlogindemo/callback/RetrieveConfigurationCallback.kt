package de.telekom.scqrlogindemo.callback

import de.telekom.scqrlogindemo.model.UserConfiguration

interface RetrieveConfigurationCallback {

    fun onConfigurationRetrieved(configuration: UserConfiguration?)

    fun onFailedToRetrieveConfiguration()
}