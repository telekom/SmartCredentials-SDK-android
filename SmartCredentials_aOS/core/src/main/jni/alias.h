extern "C" {

#include <jni.h>

jstring Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias(JNIEnv *env);

int Java_de_telekom_smartcredentials_core_rootdetector_RootDetectionNative_checkRooted(JNIEnv *env,
                                                                                       jobject thiz,
                                                                                       jobjectArray pathsArray);
}