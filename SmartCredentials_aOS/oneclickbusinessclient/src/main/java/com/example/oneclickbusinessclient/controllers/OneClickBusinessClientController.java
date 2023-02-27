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

package com.example.oneclickbusinessclient.controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oneclickbusinessclient.firebase.FirebaseTokenProvider;
import com.example.oneclickbusinessclient.recommendation.PreferencesHelper;
import com.example.oneclickbusinessclient.recommendation.Recommendation;
import com.example.oneclickbusinessclient.recommendation.RecommendationConstants;
import com.example.oneclickbusinessclient.rest.MakeRecommendationsBody;
import com.example.oneclickbusinessclient.rest.RecommenderApi;
import com.example.oneclickbusinessclient.rest.RetrofitClient;
import com.example.oneclickbusinessclient.ui.BaseLoadingDialogFragment;
import com.example.oneclickbusinessclient.ui.DialogListener;
import com.example.oneclickbusinessclient.ui.OneClickDialogFragment;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import de.telekom.smartcredentials.core.api.OneClickApi;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.oneclickbusinessclient.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OneClickBusinessClientController implements OneClickApi{

    private final CoreController mCoreController;
    private final Context mContext;
    private final FirebaseTokenProvider firebaseTokenProvider;
    private AppCompatActivity mActivity;

    //  private final PublishSubject<String> recommendationListener;
    //  private final PublishSubject<Object> eventListener;

    private final DialogListener dialogListener = new DialogListener() {
        @Override
        public void onContinue() {
            if(mActivity != null){
                BaseLoadingDialogFragment baseLoadingDialogFragment = BaseLoadingDialogFragment.newInstance(mActivity);
                baseLoadingDialogFragment.show(mActivity.getSupportFragmentManager(), BaseLoadingDialogFragment.TAG);
            }
        }
    };

    public OneClickBusinessClientController(CoreController coreController, Context context) {
        mCoreController = coreController;
        mContext = context;
        firebaseTokenProvider = new FirebaseTokenProvider();
//        recommendationListener = PublishSubject.create();
//
//        eventListener = PublishSubject.create();
//        eventListener.subscribe(message -> {
//            if(message instanceof RecommendationReceivedEvent){
//                recommendationListener.onNext("openOneClickDialogFragment");
//                eventListener.onNext(new RecommendationAcceptedEvent());
//            }else if (message instanceof RecommendationAcceptedEvent){
//                recommendationListener.onNext("openLoadingDialogFragment");
//            }else if(message instanceof TokenResponse){
//                recommendationListener.onNext("tokenResponse - open Portal");
//            }else{
//                recommendationListener.onNext("Unknown event");
//            }
//        },
//            throwable -> recommendationListener.onNext("EXCEPTION")
//        );
    }

    @Override
    public void bind(AppCompatActivity activity){
        mActivity = activity;
    }

    @Override
    public void unbind(){
        mActivity = null;
    }

    @Override
    public void makeRecommendation(List<String> productIds, String firebaseServerKey) {
        RetrofitClient retrofitClient = new RetrofitClient();
        String recommenderUrl = retrofitClient.getRecommenderUrl();
        Retrofit retrofit = retrofitClient.createRetrofitClient(recommenderUrl);
        RecommenderApi recommenderApi = retrofit.create(RecommenderApi.class);
        //    FirebaseTokenProvider firebaseTokenProvider = new FirebaseTokenProvider();

       // Observable.just(firebaseTokenProvider.getToken())

        Observable.create(firebaseTokenProvider)
                .flatMap(token -> {
                    MakeRecommendationsBody body = new MakeRecommendationsBody(token,
                            productIds, firebaseServerKey);
                    return recommenderApi.observeMakeRecommendations(body);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    @Override
    public void pushRecommendationMessage(Map<String, String> data) {
        String recommendationId = data.get(RecommendationConstants.KEY_RECOMMENDATION_ID);
        String clientId = data.get(RecommendationConstants.KEY_CLIENT_ID);
        Log.d(RecommendationConstants.TAG, "Received recommendation notification with ID " +
                recommendationId + "and client ID " + clientId);

        Recommendation recommendation = new Recommendation(recommendationId, clientId);
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance(mContext);
        preferencesHelper.saveRecommendation(recommendation);

        showDialog();
        //   eventListener.onNext(new RecommendationReceivedEvent());
    }

    @Override
    public void updateFirebaseToken(String token) {
        firebaseTokenProvider.updateToken(token);
    }

    private void showDialog(){
        if(mActivity != null){
            OneClickDialogFragment oneClickDialogFragment = OneClickDialogFragment.newInstance(R.string.one_click_dialog_title,
                    R.string.one_click_after_description, mActivity, dialogListener);
            oneClickDialogFragment.show(mActivity.getSupportFragmentManager(), OneClickDialogFragment.TAG);
        }
    }

}
