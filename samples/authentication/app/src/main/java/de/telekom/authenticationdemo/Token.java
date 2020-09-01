package de.telekom.authenticationdemo;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public class Token {

    public final static int DEFAULT_VALIDITY = -1;

    private final int resId;
    private final String token;
    private final long validity;

    public Token(int resId, String token, long validity) {
        this.resId = resId;
        this.token = token;
        this.validity = validity;
    }

    public int getResId() {
        return resId;
    }

    public String getToken() {
        return token;
    }

    public long getValidity() {
        return validity;
    }
}
