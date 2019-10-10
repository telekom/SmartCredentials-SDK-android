///*
// * Developed by Lucian Iacob in Cluj-Napoca.
// * Project: SmartCredentials_aOS
// * Email: lucian.iacob@endava.com
// * Last modified 06/11/18 16:11.
// * Copyright (c) Deutsche Telekom, 2018. All rights reserved.
// */
//
//package de.telekom.smartcredentials.core.strategies;
//
//import android.os.Build;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.mockito.Mockito;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//
//import de.telekom.smartcredentials.core.exceptions.EncryptionException;
//import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;
//import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
//import de.telekom.smartcredentials.security.encryption.Base64EncryptionManager;
//import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerAES;
//import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerRSA;
//import de.telekom.smartcredentials.security.encryption.EncryptionError;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.powermock.api.mockito.PowerMockito.when;
//
//public class EncryptionStrategyAdapterTest {
//
//    private static final int API_22 = 22;
//    private static final int API_23 = 23;
//    private final String mRandomString = "Some string";
//    private static final String SDK_INT = "SDK_INT";
//    private EncryptionStrategyAdapter mEncryptionStrategyAdapter;
//    private String mAlias;
//    private Base64EncryptionManager mBase64EncryptionManager;
//    private Base64EncryptionManagerRSA mBase64EncryptionManagerRSA;
//    private Base64EncryptionManagerAES mBase64EncryptionManagerAES;
//
//    @Rule
//    public ExpectedException mExpectedException = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        mBase64EncryptionManager = Mockito.mock(Base64EncryptionManager.class);
//        mBase64EncryptionManagerRSA = Mockito.mock(Base64EncryptionManagerRSA.class);
//        mBase64EncryptionManagerAES = Mockito.mock(Base64EncryptionManagerAES.class);
//        mEncryptionStrategyAdapter = new EncryptionStrategyAdapter(mBase64EncryptionManager,
//                mBase64EncryptionManagerRSA,
//                mBase64EncryptionManagerAES);
//        mAlias = "alias";
//    }
//
//    private static void setFinalStatic(Field field, Object newValue) throws Exception {
//        field.setAccessible(true);
//
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
//        modifiersField.setAccessible(true);
//        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//
//        field.set(null, newValue);
//    }
//
//    @Test
//    public void encryptCallsEncryptOnManager() throws EncryptionException {
//        mEncryptionStrategyAdapter.encrypt(mRandomString, mAlias);
//        verify(mBase64EncryptionManager).encrypt(mRandomString, mAlias);
//    }
//
//    @Test
//    public void encryptThrowsEncryptionException() throws EncryptionException {
//        String exceptionMessage = "could not encrypt text";
//        when(mBase64EncryptionManager.encrypt(mRandomString, mAlias)).thenThrow(new EncryptionException(exceptionMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(exceptionMessage);
//
//        mEncryptionStrategyAdapter.encrypt(mRandomString, mAlias);
//    }
//
//    @Test
//    public void decryptCallsDecryptOnManager() throws EncryptionException {
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias);
//        verify(mBase64EncryptionManager).decrypt(mRandomString, mAlias);
//    }
//
//    @Test
//    public void decryptThrowsEncryptionException() throws EncryptionException {
//        String exceptionMessage = "could not decrypt text";
//        when(mBase64EncryptionManager.decrypt(mRandomString, mAlias)).thenThrow(
//                new EncryptionException(exceptionMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(exceptionMessage);
//
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias);
//    }
//
//    @Test
//    public void decryptWithRsaThrowsEncryptionException() throws EncryptionException {
//        String exceptionMessage = "Error decrypting";
//        when(mBase64EncryptionManagerRSA.decrypt(mRandomString, mAlias)).thenThrow(
//                new EncryptionException(exceptionMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(exceptionMessage);
//
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias, EncryptionAlgorithm.RSA_2048);
//    }
//
//    @Test
//    public void decryptWithDefaultThrowsEncryptionException() throws EncryptionException {
//        String exceptionMessage = "Error decrypting";
//        when(mBase64EncryptionManager.decrypt(mRandomString, mAlias)).thenThrow(
//                new EncryptionException(exceptionMessage));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(exceptionMessage);
//
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias, EncryptionAlgorithm.DEFAULT);
//    }
//
//    @Test
//    public void decryptWithRsaCallsMethodOnRsaManager() throws EncryptionException {
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias, EncryptionAlgorithm.RSA_2048);
//
//        verify(mBase64EncryptionManagerRSA, times(1)).decrypt(mRandomString, mAlias);
//        verify(mBase64EncryptionManager, never()).decrypt(anyString(), anyString());
//    }
//
//    @Test
//    public void decryptWithAesCallsMethodOnEncryptionManager() throws EncryptionException {
//        mEncryptionStrategyAdapter.decrypt(mRandomString, mAlias, EncryptionAlgorithm.AES_256);
//
//        verify(mBase64EncryptionManagerRSA, never()).decrypt(anyString(), anyString());
//        verify(mBase64EncryptionManager, times(1)).decrypt(mRandomString, mAlias);
//    }
//
//    @Test
//    public void getPublicKeyCallsMethodOnRsaManager() throws EncryptionException {
//        mEncryptionStrategyAdapter.getPublicKey(mAlias);
//
//        verify(mBase64EncryptionManagerRSA, times(1)).getPublicKey(mAlias);
//    }
//
//    @Test
//    public void getPublicKeyThrowsEncryptionException() throws EncryptionException {
//        when(mBase64EncryptionManagerRSA.getPublicKey(mAlias)).thenThrow(new EncryptionException(mRandomString));
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(mRandomString);
//
//        mEncryptionStrategyAdapter.getPublicKey(mAlias);
//    }
//
//    @Test
//    public void encryptWithAlgorithmThrowsInvalidAlgorithmExceptionWhenSdkIsLowerThanMarshmallowAndAlgorithmIsAes()
//            throws Exception {
//        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_22);
//
//        mExpectedException.expect(InvalidAlgorithmException.class);
//        mExpectedException.expectMessage(EncryptionError.ALGORITHM_NOT_SUPPORTED);
//
//        mEncryptionStrategyAdapter.encrypt("", "", EncryptionAlgorithm.AES_256);
//    }
//
//    @Test
//    public void encryptWithAlgorithmThrowsEncryptionExceptionWhenEncryptionManagerThrowsEncryptionException()
//            throws EncryptionException {
//        String toEncrypt = "toEncrypt";
//        String alias = "alias";
//        when(mEncryptionStrategyAdapter.encrypt(toEncrypt, alias))
//                .thenThrow(new EncryptionException(EncryptionError.ENCRYPTION_EXCEPTION_TEXT));
//
//        mExpectedException.expect(EncryptionException.class);
//        mExpectedException.expectMessage(EncryptionError.ENCRYPTION_EXCEPTION_TEXT);
//
//        mEncryptionStrategyAdapter.encrypt(toEncrypt, alias, EncryptionAlgorithm.DEFAULT);
//    }
//
//    @Test
//    public void encryptCallsMethodOnRsaManagerWhenAlgorithmIsRsa() throws EncryptionException {
//        String toEncrypt = "toEncrypt";
//        String alias = "alias";
//
//        mEncryptionStrategyAdapter.encrypt(toEncrypt, alias, EncryptionAlgorithm.RSA_2048);
//
//        verify(mBase64EncryptionManagerRSA, times(1)).encrypt(toEncrypt, alias);
//        verifyZeroInteractions(mBase64EncryptionManager);
//        verifyZeroInteractions(mBase64EncryptionManagerAES);
//    }
//
//    @Test
//    public void encryptCallsMethodOnAesManagerWhenAlgorithmIsAes() throws Exception {
//        String toEncrypt = "toEncrypt";
//        String alias = "alias";
//
//        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_23);
//        mEncryptionStrategyAdapter.encrypt(toEncrypt, alias, EncryptionAlgorithm.AES_256);
//
//        verify(mBase64EncryptionManagerAES, times(1)).encrypt(toEncrypt, alias);
//        verifyZeroInteractions(mBase64EncryptionManager);
//        verifyZeroInteractions(mBase64EncryptionManagerRSA);
//    }
//
//    @Test
//    public void encryptCallsMethodOnRsaManagerWhenAlgorithmIsDefault() throws EncryptionException {
//        String toEncrypt = "toEncrypt";
//        String alias = "alias";
//
//        mEncryptionStrategyAdapter.encrypt(toEncrypt, alias, EncryptionAlgorithm.DEFAULT);
//
//        verify(mBase64EncryptionManager, times(1)).encrypt(toEncrypt, alias);
//        verifyZeroInteractions(mBase64EncryptionManagerRSA);
//        verifyZeroInteractions(mBase64EncryptionManagerAES);
//    }
//}