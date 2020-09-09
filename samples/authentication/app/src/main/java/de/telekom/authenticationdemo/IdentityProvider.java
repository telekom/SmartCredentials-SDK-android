package de.telekom.authenticationdemo;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public enum IdentityProvider {
    GOOGLE("GOOGLE", R.raw.google_openid_config);

    private final String name;
    private final int configId;

    IdentityProvider(String name, int configId) {
        this.name = name;
        this.configId = configId;
    }

    public String getName() {
        return name;
    }

    public int getConfigId() {
        return configId;
    }
}
