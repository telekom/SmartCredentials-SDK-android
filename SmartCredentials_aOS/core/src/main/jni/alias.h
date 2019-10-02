extern "C" {

#include <jni.h>

extern "C" jstring Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias(JNIEnv *env,
                                                                                   jclass type);

extern "C" int
Java_de_telekom_smartcredentials_core_rootdetector_RootDetectionNative_checkRooted(JNIEnv *env,
                                                                                   jobject instance,
                                                                                   jobjectArray pathArray);

extern "C" jstring Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias__Z(JNIEnv *env,
                                                                                      jclass type,
                                                                                      jboolean isSensitive);
}