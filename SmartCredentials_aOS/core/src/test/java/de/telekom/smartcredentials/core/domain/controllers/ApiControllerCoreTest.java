///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 18/01/19 12:37.
// * Copyright (c) Deutsche Telekom, 2019. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.domain.controllers;
//
//import android.text.TextUtils;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.core.exceptions.UserNotSetException;
//import de.telekom.smartcredentials.core.model.DomainModelException;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.plugins.SecurityLeakCheckWrapper;
//import de.telekom.smartcredentials.persistence.domain.task.TaskManager;
//import de.telekom.smartcredentials.core.domain.utils.MocksProvider;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//
//@SuppressWarnings("unchecked")
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(TextUtils.class)
//public class ApiControllerCoreTest {
//
//    @Rule
//    public  ExpectedException        mExpectedException = ExpectedException.none();
//    private CoreController        mApiController;
//    private SecurityLeakCheckWrapper mSecurityLeakCheckWrapper;
//    private FlowController           mFlowController;
//
//    @Before
//    public void getInstance() {
//        PowerMockito.mockStatic(TextUtils.class);
//
//        mSecurityLeakCheckWrapper = MocksProvider.provideSecurityLeackCheckWrapper();
//        TaskManager taskManager = MocksProvider.provideTaskManager();
//        mFlowController = MocksProvider.provideFlowController();
//
//        mApiController = new CoreController(mFlowController,
//                                               mSecurityLeakCheckWrapper,
//                                               taskManager,
//                                               true);
//    }
//
//    @Test
//    public void setUserIdThrowsExceptionIsUserIdIsEmpty() {
//        String userId = "";
//        PowerMockito.when(TextUtils.isEmpty(userId)).thenReturn(true);
//        mExpectedException.expect(UserNotSetException.class);
//
//        mApiController.setUserId(userId);
//    }
//
//    @Test
//    public void isSecurityCompromisedCallsMethodOnSecurityLeakCheckWrapper() {
//        mApiController.isSecurityCompromised();
//
//        verify(mSecurityLeakCheckWrapper, times(1)).isSecurityCompromised();
//    }
//
//    @Test
//    public void isSecurityLeakDetectedCallsMethodOnSecurityLeakCheckWrapper() {
//        mApiController.isSecurityLeakDetected();
//
//        verify(mSecurityLeakCheckWrapper, times(1)).isSecurityCompromised();
//    }
//
//    @Test
//    public void validateItemDomainModelThrowsException() {
//        PowerMockito.when(TextUtils.isEmpty(null)).thenReturn(true);
//        mExpectedException.expect(DomainModelException.class);
//
//        mApiController.validateItemDomainModel(new ItemDomainModel());
//    }
//
//    @Test
//    public void validateItemDomainModelDoesNotThrowsException() {
//        mApiController.validateItemDomainModel(new ItemDomainModel().setId("sa"));
//    }
//
//    @Test
//    public void executeMethodCallsFunctionOnItemHandler() {
//        ItemDomainModel itemDomainModel = new ItemDomainModel();
//        String actionId = "1";
//
//        mApiController.execute(itemDomainModel, actionId, null);
//
//        verify(mFlowController, times(1)).executeAction(itemDomainModel, actionId, null);
//    }
//}