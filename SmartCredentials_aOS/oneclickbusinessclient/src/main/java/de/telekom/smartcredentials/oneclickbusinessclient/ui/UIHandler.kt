package de.telekom.smartcredentials.oneclickbusinessclient.ui

import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickBusinessClientController
import de.telekom.smartcredentials.oneclickbusinessclient.controllers.OneClickListener
import de.telekom.smartcredentials.oneclickbusinessclient.di.ObjectGraphCreatorOneClickBusinessClient
import de.telekom.smartcredentials.oneclickbusinessclient.ui.activity.PortalActivity

class UIHandler(
    private val oneClickBusinessClientController: OneClickBusinessClientController
) : OneClickListener {

    private var mActivity: AppCompatActivity? = null
    private var mComposeView: ComposeView? = null
    private val recommendationFlowRunning = mutableStateOf(false)

    fun bind(
        activity: AppCompatActivity
    ) {
        mActivity = activity
    }

    fun setComposeView(
        composeView: ComposeView?
    ) {
        mComposeView = composeView
    }

    fun unbind() {
        mActivity = null
        mComposeView = null
    }

    private fun setupComposeView() {
        if (mActivity != null) {
            mComposeView?.setContent {
                OneClickScreen(
                    ObjectGraphCreatorOneClickBusinessClient
                        .provideViewModelOneClickClient(oneClickBusinessClientController),
                    showComposeFlow = recommendationFlowRunning.value,
                    signalFlowEnd = {
                        recommendationFlowRunning.value = false
                    },
                    signalTokenReceived = { tokenResponse ->
                        recommendationFlowRunning.value = false
                        oneClickBusinessClientController.createPortalOffer(tokenResponse.data)
                    }
                )
            }
        } else {
            ApiLoggerResolver.logError(javaClass.name, "No activity bind")
        }
    }

    override fun onRecommendationReceived() {
        recommendationFlowRunning.value = true
        setupComposeView()
    }

    override fun onOfferAvailable(offer: PortalOffer) {
        mActivity?.let { activity ->
            activity.startActivity(PortalActivity.newIntent(activity, offer))
        }
    }
}