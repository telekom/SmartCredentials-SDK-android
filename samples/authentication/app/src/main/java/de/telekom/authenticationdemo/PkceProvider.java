package de.telekom.authenticationdemo;

import android.util.Base64;

import net.openid.appauth.AuthorizationRequest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import de.telekom.smartcredentials.core.authentication.configuration.PkceConfiguration;

/**
 * Created by gabriel.blaj@endava.com at 10/6/2020
 */
public class PkceProvider {

    public PkceConfiguration generatePkceConfiguration() {
        String verifier = generateCodeVerifier();
        return new PkceConfiguration(verifier, generateCodeChallenge(verifier), AuthorizationRequest.CODE_CHALLENGE_METHOD_S256);
    }

    private String generateCodeVerifier() {
        SecureRandom sr = new SecureRandom();
        byte[] code = new byte[32];
        sr.nextBytes(code);
        return Base64.encodeToString(code, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
    }

    private String generateCodeChallenge(String verifier) {
        byte[] bytes;
        bytes = verifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(md).update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        return Base64.encodeToString(digest, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
    }
}
