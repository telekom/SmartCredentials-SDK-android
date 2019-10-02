///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 16/11/18 18:05.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.task;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//
//import de.telekom.smartcredentials.core.callback.OTPHandlerCallback;
//import de.telekom.smartcredentials.core.callback.OTPHandlerFailed;
//import de.telekom.smartcredentials.core.domain.controllers.ApiController;
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
//import de.telekom.smartcredentials.core.model.token.TokenRequest;
//import de.telekom.smartcredentials.twofactorauth.otp.OTPHandler;
//import de.telekom.smartcredentials.twofactorauth.otp.OTPUpdateCallback;
//
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//public class OTPHandlerResolverTest {
//
//    private OTPHandlerResolver mOTPHandlerResolver;
//    private OTPUpdateCallback mOTPUpdateCallback;
//    private ApiController mApiController;
//    private OTPHandlerCallback mOTPHandlerCallback;
//    private ItemDomainModel mItemDomainModel;
//    private TokenRequest mTokenRequest;
//
//    @Before
//    public void setUp() {
//        mOTPUpdateCallback = Mockito.mock(OTPUpdateCallback.class);
//        mOTPHandlerResolver = new OTPHandlerResolver(mOTPUpdateCallback);
//        mApiController = Mockito.mock(ApiController.class);
//        mOTPHandlerCallback = Mockito.mock(OTPHandlerCallback.class);
//        mItemDomainModel = Mockito.mock(ItemDomainModel.class);
//        mTokenRequest = Mockito.mock(TokenRequest.class);
//    }
//
//    @Test
//    public void resolveHandlerNotifiesOtpItemNotFound() throws Exception {
//        when(mTokenRequest.getEncryptedModel()).thenReturn(null);
//        when(mApiController.retrieveTokenRequest(mItemDomainModel)).thenReturn(mTokenRequest);
//
//        mOTPHandlerResolver.resolveHandler(mApiController, mOTPHandlerCallback, mItemDomainModel);
//
//        verify(mOTPHandlerCallback, times(1))
//                .onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_ITEM_NOT_FOUND);
//    }
//
//    @Test
//    public void resolveHandlerNotifiesOtpItemDecryptionFailedIfHandlerWasNotRetrieved()
//            throws EncryptionException {
//        when(mTokenRequest.getEncryptedModel()).thenReturn("some encrypted text");
//        when(mApiController.retrieveTokenRequest(mItemDomainModel)).thenReturn(mTokenRequest);
//
//        mOTPHandlerResolver.resolveHandler(mApiController, mOTPHandlerCallback, mItemDomainModel);
//
//        verify(mOTPHandlerCallback, times(1))
//                .onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_HANDLER_NOT_DEFINED);
//    }
//
//    @Test
//    public void resolveHandlerNotifiesOtpHandlerNotFound() throws EncryptionException {
//        when(mTokenRequest.getEncryptedModel()).thenReturn("some encrypted text");
//        when(mApiController.retrieveTokenRequest(mItemDomainModel)).thenReturn(mTokenRequest);
//        when(mApiController.getOTPHandler(mTokenRequest)).thenReturn(null);
//
//        mOTPHandlerResolver.resolveHandler(mApiController, mOTPHandlerCallback, mItemDomainModel);
//
//        verify(mOTPHandlerCallback, times(1))
//                .onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_HANDLER_NOT_DEFINED);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void resolveHandlerNotifiesOtpHandlerReady() throws EncryptionException {
//        when(mTokenRequest.getEncryptedModel()).thenReturn("some encrypted text");
//        when(mApiController.retrieveTokenRequest(mItemDomainModel)).thenReturn(mTokenRequest);
//        OTPHandler otpHandler = Mockito.mock(OTPHandler.class);
//        when(mApiController.getOTPHandler(mTokenRequest)).thenReturn(otpHandler);
//
//        mOTPHandlerResolver.resolveHandler(mApiController, mOTPHandlerCallback, mItemDomainModel);
//
//        verify(otpHandler, times(1)).getInstance(mTokenRequest, mOTPUpdateCallback);
//        verify(mOTPHandlerCallback, times(1)).onOTPHandlerReady(otpHandler);
//    }
//
//}