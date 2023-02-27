package com.example.oneclickbusinessclient.di;

import android.content.Context;

import com.example.oneclickbusinessclient.controllers.OneClickBusinessClientController;

import org.jetbrains.annotations.NotNull;

import de.telekom.smartcredentials.core.controllers.CoreController;

public class ObjectGraphCreatorOneClickBusinessClient {

    private static ObjectGraphCreatorOneClickBusinessClient sInstance;

    private ObjectGraphCreatorOneClickBusinessClient() {
        //required empty constructor
    }

    public static ObjectGraphCreatorOneClickBusinessClient getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorOneClickBusinessClient();
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
    }

    @NotNull
    public OneClickBusinessClientController provideApiControllerOneClickBusinessClient(CoreController coreController, Context context) {
        return new OneClickBusinessClientController(coreController, context);
    }
}
