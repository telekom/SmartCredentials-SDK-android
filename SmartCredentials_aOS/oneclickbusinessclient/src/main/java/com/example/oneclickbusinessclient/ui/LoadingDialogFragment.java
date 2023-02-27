package com.example.oneclickbusinessclient.ui;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.oneclickbusinessclient.identityProvider.TokenResponse;
import com.example.oneclickbusinessclient.identityProvider.TokenRetrieveState;
import com.example.oneclickbusinessclient.recommendation.RecommendationConstants;

import de.telekom.smartcredentials.oneclickbusinessclient.R;

public class LoadingDialogFragment {

    /*public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }

    @Override
    public void provideActivity(AppCompatActivity activity) {

    }

    @Override
    public void handleUnsuccessfulFetch() {
        handler.postDelayed(() -> {
            {
                Log.d(RecommendationConstants.TAG, "abcdef");
                retrievingTokenEllipsize.setVisibility(View.INVISIBLE);
                retrievingTokenCheckMark.setVisibility(View.VISIBLE);
                transferringToPortalTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textColor));
                handler.postDelayed(() -> onLoadingFinished(new TokenResponse(TokenRetrieveState.SUCCESSFUL, "abcdef")),
                        FINISH_DELAY);
            }
        }, 2000);
    }

    @Override
    public void handleInvalidFetch() {
        handler.postDelayed(() -> {
            {
                Log.d(RecommendationConstants.TAG, "abcdef");
                retrievingTokenEllipsize.setVisibility(View.INVISIBLE);
                retrievingTokenCheckMark.setVisibility(View.VISIBLE);
                transferringToPortalTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textColor));
                handler.postDelayed(() -> onLoadingFinished(new TokenResponse(TokenRetrieveState.SUCCESSFUL, "abcdef")),
                        FINISH_DELAY);
            }
        }, 2000);
    }*/
}
