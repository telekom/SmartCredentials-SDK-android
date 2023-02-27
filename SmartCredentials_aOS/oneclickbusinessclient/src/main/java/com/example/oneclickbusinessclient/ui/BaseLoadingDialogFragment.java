package com.example.oneclickbusinessclient.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.oneclickbusinessclient.identityProvider.TokenResponse;
import com.example.oneclickbusinessclient.identityProvider.TokenRetrieveState;
import com.example.oneclickbusinessclient.identityProvider.TransactionTokenListener;
import com.example.oneclickbusinessclient.recommendation.RecommendationConstants;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

import de.telekom.smartcredentials.oneclickbusinessclient.R;
import io.reactivex.disposables.CompositeDisposable;

public class BaseLoadingDialogFragment extends DialogFragment implements TransactionTokenListener {

    public final static String TAG = "LoadingDialogFragment";

    public static final int DEFAULT_DELAY = 500;
    public static final int FINISH_DELAY = 2000;
    //private ContentProviderManager contentProviderManager;
    private static AppCompatActivity mActivity;
    protected TextView retrievingTokenTextView;
    protected TextView retrievingTokenEllipsize;
    protected ImageView retrievingTokenCheckMark;
    protected TextView transferringToPortalTextView;
    protected Handler handler;
    private CompositeDisposable compositeDisposable;

    public static BaseLoadingDialogFragment newInstance(AppCompatActivity activity) {
        BaseLoadingDialogFragment loadingDialogFragment = new BaseLoadingDialogFragment();
        mActivity = activity;
        return loadingDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext(),
                R.style.MaterialRoundedAlertDialogStyle);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_loading, null);
        retrievingTokenTextView = view.findViewById(R.id.retrieving_token_text);
        retrievingTokenEllipsize = view.findViewById(R.id.retrieving_token_ellipsize);
        retrievingTokenCheckMark = view.findViewById(R.id.retrieving_token_check_icon);
        transferringToPortalTextView = view.findViewById(R.id.transferring_to_portal_text);
        Button dismissButton = view.findViewById(R.id.dismiss_button);
        dismissButton.setOnClickListener(v -> {
            handler.removeCallbacksAndMessages(null);
            dismiss();
        });
        // contentProviderManager = new ContentProviderManager(view.getContext());
        handler = new Handler();
        builder.setView(view);
        builder.setCancelable(false);
        //  startFlow();
        return builder.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog()).setCancelable(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onSuccessfulFetch(String token) {
        handler.postDelayed(() -> {
            {
                Log.d(RecommendationConstants.TAG, token);
                retrievingTokenEllipsize.setVisibility(View.INVISIBLE);
                retrievingTokenCheckMark.setVisibility(View.VISIBLE);
                transferringToPortalTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textColor));
                handler.postDelayed(() -> onLoadingFinished(new TokenResponse(TokenRetrieveState.SUCCESSFUL, token)), FINISH_DELAY);
            }
        }, 2000);
    }

    @Override
    public void onUnsuccessfulFetch() {
        Log.d(RecommendationConstants.TAG, "unsuccessful");
        handleUnsuccessfulFetch();
    }

    @Override
    public void onInvalidFetch() {
        Log.d(RecommendationConstants.TAG, "invalid");
        handleInvalidFetch();
    }

  /*  private void startFlow() {
        handler.postDelayed(() -> {
            {
                retrievingTokenTextView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textColor));
                retrievingTokenEllipsize.setTextColor(ContextCompat.getColor(requireActivity(), R.color.textColor));
                Retrofit retrofitClient = new RetrofitClient().createRetrofitClient(RetrofitClient.PARTNER_APPLICATION_URL);
                PartnerManagementApi api = retrofitClient.create(PartnerManagementApi.class);
                Recommendation recommendation = PreferencesHelper.getInstance(requireActivity()).retrieveRecommendation();
                compositeDisposable.add(api.observeAccessToken("Moonlight-017e56d0-7997-44da-bac6-a3c3f4a42bea")
                        .flatMap(accessToken -> {
                            GetBearerBody body = new GetBearerBody(accessToken, "oneclickdemo", null, requireActivity().getPackageName());
                            return api.observeBearerToken(body);
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bearerToken -> contentProviderManager.getTransactionToken(bearerToken, recommendation.getClientId(), this),
                                throwable -> {
                                    Log.e(RecommendationConstants.TAG, throwable.getLocalizedMessage());
                                    onLoadingFinished(new TokenResponse(TokenRetrieveState.UNSUCCESSFUL, throwable.getLocalizedMessage()));
                                }));
            }
        }, DEFAULT_DELAY);
    }*/

    protected void onLoadingFinished(TokenResponse response) {
        handler.postDelayed(() -> {
            {
                //token
                dismiss();
            }
        }, DEFAULT_DELAY);
    }

    public void handleUnsuccessfulFetch() {

    }

    public void handleInvalidFetch() {

    }
}