package com.operatortokenocb.contentprovider;

public interface TransactionTokenListener {

    void onSuccessfulFetch(String token);

    void onUnsuccessfulFetch();

    void onInvalidFetch();
}
