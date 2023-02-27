package com.example.oneclickbusinessclient.firebase;

import com.google.firebase.messaging.FirebaseMessaging;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class FirebaseTokenProvider implements ObservableOnSubscribe<String> {

    private String firebaseToken;

    public void updateToken(String token) {
        firebaseToken = token;
    }

    public String getToken(){
        return firebaseToken;
    }

    @Override
    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                emitter.onError(new FirebaseTokenException("Could not get Firebase token."));
            }
            emitter.onNext(task.getResult());
        });
    }
}
