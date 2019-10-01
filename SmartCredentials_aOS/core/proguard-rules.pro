# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\mabica\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#classes in api module
-keep public class de.telekom.smartcredentials.core.api.** { *; }
-keep public class de.telekom.smartcredentials.core.di.** { *; }
-keep public class de.telekom.smartcredentials.core.filter.** { *; }
-keep public class de.telekom.smartcredentials.core.itemdatamodel.** { *; }
-keep public class de.telekom.smartcredentials.core.responses.** { *; }
-keep public class de.telekom.smartcredentials.core.context.** { *; }
-keep public class de.telekom.smartcredentials.core.callback.** { *; }
-keep public class de.telekom.smartcredentials.core.logger.** { *; }
-keep public class de.telekom.smartcredentials.core.blacklisting.** { *; }
-keep public class de.telekom.smartcredentials.core.actions.** { *; }
-keep public class de.telekom.smartcredentials.core.domain.** { *; }
-keep public class de.telekom.smartcredentials.core.rootdetector.** { *; }

-keep public class de.telekom.smartcredentials.authentication.api.** { *; }
-keep public class de.telekom.smartcredentials.camera.api.** { *; }
-keep public class de.telekom.smartcredentials.documentscanner.api.** { *; }
-keep public class de.telekom.smartcredentials.networking.api.** { *; }
-keep public class de.telekom.smartcredentials.persistence.api.** { *; }
-keep public class de.telekom.smartcredentials.security.api.** { *; }
-keep public class de.telekom.smartcredentials.otp.api.** { *; }
-keep public class de.telekom.smartcredentials.core.wispr.** { *; }


#classes in authorization module
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintView {
    <methods>;
}
-keep public interface de.telekom.smartcredentials.authorization.fragments.presenters.FingerprintView {
    <methods>;
}
-keep public enum de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable
-keepclassmembers public enum de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public enum de.telekom.smartcredentials.core.authorization.AuthorizationPluginError
-keepclassmembers public enum de.telekom.smartcredentials.core.authorization.AuthorizationPluginError {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#classes in camera module
-keep public class de.telekom.smartcredentials.core.camera.CameraScannerLayout
-keepclassmembers public class de.telekom.smartcredentials.core.camera.CameraScannerLayout {
    public void stopScanner();
    public void startScanner();
    public void releaseCamera();
}
-keep public enum de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable
-keepclassmembers public enum de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class de.telekom.smartcredentials.camera.ocr.OcrCameraScannerLayout
-keepclassmembers public class de.telekom.smartcredentials.camera.ocr.OcrCameraScannerLayout {
    public void detect(***, ***, ***);
    public void detect(***, ***);
    public void detect(***);
    public void detect();
}
-keep public class de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayout
-keepclassmembers public class de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayout {
    public void startScanner(***);
}
-keep public class de.telekom.smartcredentials.camera.ocr.TextParser
-keepclassmembers public class de.telekom.smartcredentials.camera.ocr.TextParser {
    *** parse(***);
}
-keep public class de.telekom.smartcredentials.camera.ocr.OnBitmapLoadedCallback
-keepclassmembers public class de.telekom.smartcredentials.camera.ocr.OnBitmapLoadedCallback {
    *** onLoaded(***);
}

#classes in documentscanner module
-keep public class de.telekom.smartcredentials.documentscanner.config.** { *; }
-keep public class de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout
-keepclassmembers public class de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout {
    public <methods>;
}
-keep public class de.telekom.smartcredentials.documentscanner.presenter.DocumentScannerPresenter
-keep public class de.telekom.smartcredentials.documentscanner.view.DocumentScannerView
-keep public class de.telekom.smartcredentials.documentscanner.model.** { *; }
-keep public enum de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable
-keepclassmembers public enum de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#classes in domain module
-keep public enum de.telekom.smartcredentials.core.repositories.RepoError
-keepclassmembers public enum de.telekom.smartcredentials.core.repositories.RepoError {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public enum de.telekom.smartcredentials.core.model.EncryptionAlgorithm
-keepclassmembers public enum de.telekom.smartcredentials.core.model.EncryptionAlgorithm {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class de.telekom.smartcredentials.core.model.otp.OTPTokenKey
-keepclassmembers public class de.telekom.smartcredentials.core.model.otp.OTPTokenKey {
    <fields>;
}
-keep public enum de.telekom.smartcredentials.core.model.otp.OTPType
-keepclassmembers public enum de.telekom.smartcredentials.core.model.otp.OTPType {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras
-keepclassmembers public class de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras {
    <fields>;
}
-keep public class de.telekom.smartcredentials.core.model.request.RequestParams
-keep public class de.telekom.smartcredentials.core.model.request.FailedRequest {
    public <methods>;
}
-keep public class de.telekom.smartcredentials.core.model.DocumentScannerResult
-keep public class de.telekom.smartcredentials.core.model.token.TokenResponse
-keepclassmembers public class de.telekom.smartcredentials.core.model.token.TokenResponse {
    public <methods>;
}
-keep public class de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback
-keep public class de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback
-keep public class de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintAuthorizationPresenter
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.AuthorizationPresenter
-keepclassmembers public interface de.telekom.smartcredentials.core.plugins.fingerprint.AuthorizationPresenter {
    public <methods>;
}
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView
-keepclassmembers public interface de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView {
    public <methods>;
}
-keep public interface de.telekom.smartcredentials.core.plugins.fingerprint.FingerprintView

#classes in networking module
-keep public class de.telekom.smartcredentials.networking.AuthParamKey
-keepclassmembers public class de.telekom.smartcredentials.networking.AuthParamKey {
    <fields>;
}
-keep public enum de.telekom.smartcredentials.core.qrlogin.TokenPluginError
-keepclassmembers public enum de.telekom.smartcredentials.core.qrlogin.TokenPluginError {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public enum de.telekom.smartcredentials.core.networking.RequestFailureLevel
-keepclassmembers public enum de.telekom.smartcredentials.core.networking.RequestFailureLevel {
    <fields>;
}

#classes in thwofactorauth
-keep public interface de.telekom.smartcredentials.twofactorauth.HOTPHandler
-keepclassmembers public interface de.telekom.smartcredentials.twofactorauth.HOTPHandler {
    public <methods>;
}
-keep public class de.telekom.smartcredentials.twofactorauth.HOTPCallback
-keep public interface de.telekom.smartcredentials.twofactorauth.TOTPHandler
-keepclassmembers public interface de.telekom.smartcredentials.twofactorauth.TOTPHandler {
    public <methods>;
}
-keep public class de.telekom.smartcredentials.twofactorauth.TOTPCallback
-keep public class de.telekom.smartcredentials.twofactorauth.OTPCallback
-keepclassmembers public class de.telekom.smartcredentials.twofactorauth.OTPCallback {
    public <methods>;
}
-keep public enum de.telekom.smartcredentials.twofactorauth.OTPPluginError
-keepclassmembers public enum de.telekom.smartcredentials.twofactorauth.OTPPluginError {
    <fields>;
    <methods>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#other
-keep public class * extends java.lang.Exception
-keep public class * extends java.lang.RuntimeException
-dontwarn java.lang.invoke.**
-dontwarn sun.misc.Unsafe
-dontwarn javax.annotation.**
-dontwarn de.telekom.smartcredentials.**

-keepattributes Exceptions