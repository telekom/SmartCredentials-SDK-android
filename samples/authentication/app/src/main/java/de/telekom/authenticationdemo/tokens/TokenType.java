package de.telekom.authenticationdemo.tokens;

import de.telekom.authenticationdemo.R;

/**
 * Created by Alex.Graur@endava.com at 9/29/2020
 */
public enum TokenType {

    ACCESS_TOKEN(R.string.access_token),
    ID_TOKEN(R.string.id_token),
    REFRESH_TOKEN(R.string.refresh_token);

    private final int nameResId;

    TokenType(int nameResId) {
        this.nameResId = nameResId;
    }

    public int getNameResId() {
        return nameResId;
    }
}
