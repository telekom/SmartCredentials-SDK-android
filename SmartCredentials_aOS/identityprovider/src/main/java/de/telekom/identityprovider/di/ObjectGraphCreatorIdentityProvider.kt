package de.telekom.identityprovider.di

import de.telekom.identityprovider.controller.IdentityProviderController
import de.telekom.smartcredentials.core.controllers.CoreController

object ObjectGraphCreatorIdentityProvider {

    private var sInstance: ObjectGraphCreatorIdentityProvider? = null

    fun getInstance(): ObjectGraphCreatorIdentityProvider {
        if (sInstance == null) {
            sInstance = ObjectGraphCreatorIdentityProvider
        }
        return sInstance as ObjectGraphCreatorIdentityProvider
    }


    fun provideApiControllerIdentityProvider(
        coreController: CoreController,
        baseUrl: String,
        credentials: String
    ): IdentityProviderController {
        return IdentityProviderController(coreController, baseUrl, credentials)
    }

    fun destroy() {
        sInstance = null
    }
}