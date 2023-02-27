package com.example.oneclickbusinessclient.identityProvider;

public interface TransactionTokenListener {
    
    void onSuccessfulFetch(String token);

    void onUnsuccessfulFetch();

    void onInvalidFetch();
}
