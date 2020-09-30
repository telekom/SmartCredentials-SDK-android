package de.telekom.authenticationdemo.tokens;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public class Token {

    public final static int DEFAULT_VALIDITY = -1;

    private final TokenType tokenType;
    private final String token;
    private final long validity;

    public Token(TokenType tokenType, String token, long validity) {
        this.tokenType = tokenType;
        this.token = token;
        this.validity = validity;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    public long getValidity() {
        return validity;
    }
}
