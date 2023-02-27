package com.example.oneclickbusinessclient.identityProvider;

public class TokenResponse {

    public TokenRetrieveState state;
    public String data;

    public TokenResponse(TokenRetrieveState state, String data) {
        this.state = state;
        this.data = data;
    }

    public TokenRetrieveState getState() {
        return state;
    }

    public String getData() {
        return data;
    }
}
