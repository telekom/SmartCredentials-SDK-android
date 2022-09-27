package de.telekom.camerademo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public class DemoApplication extends Application {

    public static final String TAG = "camera_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
