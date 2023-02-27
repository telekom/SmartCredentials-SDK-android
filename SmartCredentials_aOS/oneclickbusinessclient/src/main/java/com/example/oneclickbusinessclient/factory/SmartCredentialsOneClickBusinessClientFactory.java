package com.example.oneclickbusinessclient.factory;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.oneclickbusinessclient.controllers.OneClickBusinessClientController;
import com.example.oneclickbusinessclient.di.ObjectGraphCreatorOneClickBusinessClient;

import org.jetbrains.annotations.NotNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.OneClickApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;

public class SmartCredentialsOneClickBusinessClientFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials OneClickBusinessClient Module have not been initialized";

    private static OneClickBusinessClientController sOneClickBusinessClientController;

    private SmartCredentialsOneClickBusinessClientFactory() {
        // required empty constructor
    }

    @NotNull
    public static synchronized OneClickApi initSmartCredentialsOneClickBusinessClientModule(@NonNull CoreApi coreApi, @NonNull Context context) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.ONE_CLICK_BUSINESS_CLIENT_MODULE.getModuleName());
        }
        sOneClickBusinessClientController = ObjectGraphCreatorOneClickBusinessClient.getInstance()
                .provideApiControllerOneClickBusinessClient(coreController, context);
        return sOneClickBusinessClientController;
    }

    @NonNull
    public static synchronized OneClickApi getOneClickBusinessClientApi() {
        if (sOneClickBusinessClientController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sOneClickBusinessClientController;
    }

    public static void clear() {
        ObjectGraphCreatorOneClickBusinessClient.destroy();
        sOneClickBusinessClientController = null;
    }
}
