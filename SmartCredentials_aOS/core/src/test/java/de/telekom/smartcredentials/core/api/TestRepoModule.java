///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 06/11/18 15:59.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import android.content.Context;
//
//import org.mockito.Mockito;
//
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.controllers.callbackimplementations.ScannerPluginCallbackForOTP;
//import de.telekom.smartcredentials.core.plugins.SecurityLeakCheckWrapper;
//import de.telekom.smartcredentials.core.repositories.Repository;
//import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
//import de.telekom.smartcredentials.persistence.database.AppDatabase;
//import de.telekom.smartcredentials.core.task.OTPHandlerResolver;
//import de.telekom.smartcredentials.core.task.TaskManager;
//
////@Module
//public class TestRepoModule {
//
//    //    @Provides
//    Context provideContext() {
//        return Mockito.mock(Context.class);
//    }
//
//    //    @Provides
//    Repository providesRepository() {
//        return Mockito.mock(Repository.class);
//    }
//
//    //    @Provides
//    EncryptionStrategy provideEncryptionStrategy() {
//        return Mockito.mock(EncryptionStrategy.class);
//    }
//
//    //    @Provides
//    SecurityLeakCheckWrapper provideRootDetection() {
//        return Mockito.mock(SecurityLeakCheckWrapper.class);
//    }
//
//    //    @Provides
//    ApiController provideCoreController() {
//        return Mockito.mock(ApiController.class);
//    }
//
//    //    @Provides
//    AppDatabase provideItemsDatabase() {
//        return Mockito.mock(AppDatabase.class);
//    }
//
//    //    @Provides
//    TaskManager provideTaskManager() {
//        return Mockito.mock(TaskManager.class);
//    }
//
//    //    @Provides
//    ScannerPluginCallbackForOTP provideScannerPluginCallbackForOTP() {
//        return Mockito.mock(ScannerPluginCallbackForOTP.class);
//    }
//
//    //    @Provides
//    OTPHandlerResolver provideOTPHandlerResolver() {
//        return Mockito.mock(OTPHandlerResolver.class);
//    }
//}
