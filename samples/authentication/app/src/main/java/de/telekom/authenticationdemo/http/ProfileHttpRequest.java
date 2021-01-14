package de.telekom.authenticationdemo.http;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import de.telekom.authenticationdemo.profile.Profile;

/**
 * Created by Alex.Graur@endava.com at 9/2/2020
 */
public class ProfileHttpRequest {

    public static Profile getProfile(String url, String accessToken) {
        try {
            URL userInfoEndpoint = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) userInfoEndpoint.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);
            urlConnection.setInstanceFollowRedirects(false);
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return new Gson().fromJson(sb.toString(), Profile.class);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            return null;
        }
    }
}
