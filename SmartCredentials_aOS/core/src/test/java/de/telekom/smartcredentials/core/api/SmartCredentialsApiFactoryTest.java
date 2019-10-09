///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 16:53.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.api;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import de.telekom.smartcredentials.LicenseManager;
//import de.telekom.smartcredentials.core.di.ObjectGraphCreator;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.logger.ApiLogger;
//
//import static de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory.EMPTY_APP_ALIAS_EXCEPTION;
//import static de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory.EMPTY_USER_ID_EXCEPTION;
//import static de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory.MODULE_NOT_INITIALIZED_EXCEPTION;
//import static de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory.NULL_CONTEXT_EXCEPTION;
//import static junit.framework.Assert.assertNotNull;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({SmartCredentialsApiImpl.class, TextUtils.class})
//public class SmartCredentialsApiFactoryTest {
//
//    private Context mContext;
//    private String mUserId;
//    private ApiLogger mApiLogger;
//    private String mAppAlias = "_sdk";
//
//    @Rule
//    public ExpectedException thrown = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        PowerMockito.mockStatic(SmartCredentialsApiImpl.class);
//        PowerMockito.mockStatic(TextUtils.class);
//        mContext = Mockito.mock(Context.class);
//        mUserId = "1234";
//        mApiLogger = Mockito.mock(ApiLogger.class);
//    }
//
//    @Test
//    @PrepareForTest({SmartCredentialsApiImpl.class, TextUtils.class, LicenseManager.class, ObjectGraphCreator.class})
//    public void initializeReturnsSmartCredentialsApi() {
//        PowerMockito.mockStatic(LicenseManager.class);
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//
//        SmartCredentialsApiImpl mockSmartCredentialsApi = Mockito.mock(SmartCredentialsApiImpl.class);
//        ObjectGraphCreator objectCreator = Mockito.mock(ObjectGraphCreator.class);
//        ApiController apiController = Mockito.mock(ApiController.class);
//        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(mContext, mUserId)
//                .build();
//
//        PowerMockito.when(ObjectGraphCreator.getInstance()).thenReturn(objectCreator);
//        when(objectCreator.provideCoreController(mContext)).thenReturn(apiController);
//        when(SmartCredentialsApiImpl.newInstance(apiController)).thenReturn(mockSmartCredentialsApi);
//
//        SmartCredentialsApi smartCredentialsApi = SmartCredentialsCoreFactory.initialize(configuration);
//
//        verify(apiController, times(1)).setUserId(mUserId);
//        assertNotNull(smartCredentialsApi);
//    }
//
//    @Test
//    @PrepareForTest({SmartCredentialsApiImpl.class, TextUtils.class, LicenseManager.class, ObjectGraphCreator.class})
//    public void getSmartCredentialsApiReturnsNotNull() {
//        PowerMockito.mockStatic(LicenseManager.class);
//        PowerMockito.mockStatic(ObjectGraphCreator.class);
//        ObjectGraphCreator objectCreator = Mockito.mock(ObjectGraphCreator.class);
//        ApiController apiController = Mockito.mock(ApiController.class);
//        SmartCredentialsApiImpl mockSmartCredentialsApi = Mockito.mock(SmartCredentialsApiImpl.class);
//
//        PowerMockito.when(ObjectGraphCreator.getInstance()).thenReturn(objectCreator);
//        when(objectCreator.provideCoreController(mContext)).thenReturn(apiController);
//        when(SmartCredentialsApiImpl.newInstance(apiController)).thenReturn(mockSmartCredentialsApi);
//
//        SmartCredentialsCoreFactory.initSmartCredentialsApi(mContext, mUserId, mApiLogger, mAppAlias);
//
//        SmartCredentialsApi smartCredentialsApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
//
//        verify(apiController, times(1)).setUserId(mUserId);
//        assertNotNull(smartCredentialsApi);
//    }
//
//    @Test
//    public void initSmartCredentialsApiThrowsExceptionWhenUserIdIsEmpty() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(EMPTY_USER_ID_EXCEPTION);
//
//        String userId = null;
//        when(TextUtils.isEmpty(userId)).thenReturn(true);
//
//        configure();
//        SmartCredentialsApi smartCredentialsApi = SmartCredentialsCoreFactory.initSmartCredentialsApi(mContext, userId, mApiLogger, mAppAlias);
//
//        assertNotNull(smartCredentialsApi);
//    }
//
//    @Test
//    public void initSmartCredentialsApiThrowsExceptionWhenAppAliasIsEmpty() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(EMPTY_APP_ALIAS_EXCEPTION);
//
//        String appAlias = null;
//        when(TextUtils.isEmpty(appAlias)).thenReturn(true);
//
//        configure();
//        SmartCredentialsApi smartCredentialsApi = SmartCredentialsCoreFactory.initSmartCredentialsApi(mContext, mUserId, mApiLogger, appAlias);
//
//        assertNotNull(smartCredentialsApi);
//    }
//
//    @Test
//    public void initSmartCredentialsApiThrowsExceptionWhenContextIsNull() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(NULL_CONTEXT_EXCEPTION);
//
//        configure();
//        SmartCredentialsApi smartCredentialsApi = SmartCredentialsCoreFactory.initSmartCredentialsApi(null, mUserId, mApiLogger, mAppAlias);
//
//        assertNotNull(smartCredentialsApi);
//    }
//
//    @Test
//    public void getSmartCredentialsApiThrowsExceptionWhenSmartCredentialsApiPropertyNotInstantiated() {
//        SmartCredentialsCoreFactory.clear();
//        thrown.expect(RuntimeException.class);
//        thrown.expectMessage(MODULE_NOT_INITIALIZED_EXCEPTION);
//
//        SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
//    }
//
//    @Test
//    public void checkNonEmptyDoesNothingIfStringNotEmpty() {
//        when(TextUtils.isEmpty(mUserId)).thenReturn(false);
//        SmartCredentialsCoreFactory.checkNonEmpty(mUserId, EMPTY_USER_ID_EXCEPTION);
//    }
//
//    @Test
//    public void checkNonEmptyThrowsExceptionWhenStringISNull() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(EMPTY_USER_ID_EXCEPTION);
//
//        String userId = null;
//        when(TextUtils.isEmpty(userId)).thenReturn(true);
//        SmartCredentialsCoreFactory.checkNonEmpty(userId, EMPTY_USER_ID_EXCEPTION);
//    }
//
//    @Test
//    public void checkNonNullDoesNothingIfStringNotEmpty() {
//        Context context = Mockito.mock(Context.class);
//        SmartCredentialsCoreFactory.checkNonNull(context, EMPTY_USER_ID_EXCEPTION);
//    }
//
//    @Test
//    public void checkNonNullThrowsExceptionWhenStringISNull() {
//        thrown.expect(IllegalArgumentException.class);
//        thrown.expectMessage(NULL_CONTEXT_EXCEPTION);
//        SmartCredentialsCoreFactory.checkNonNull(null, NULL_CONTEXT_EXCEPTION);
//    }
//
//    private void configure() {
//        ApiController apiController = Mockito.mock(ApiController.class);
//        SmartCredentialsApiImpl smartCredentialsApi = Mockito.mock(SmartCredentialsApiImpl.class);
//
//        when(SmartCredentialsApiImpl.newInstance(apiController))
//                .thenReturn(smartCredentialsApi);
//    }
//}