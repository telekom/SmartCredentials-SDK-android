package de.telekom.smartcredentials.oneclickbusinessclient.controllers

import de.telekom.smartcredentials.oneclickbusinessclient.ui.PortalOffer

interface OneClickListener {

    fun onRecommendationReceived()

    fun onOfferAvailable(offer: PortalOffer)
}