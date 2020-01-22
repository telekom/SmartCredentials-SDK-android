extern "C" {

#include <jni.h>

jstring Java_de_telekom_smartcredentials_storage_repositories_RepositoryAliasNative_alias(JNIEnv *env,
                                                                                jclass clazz,
                                                                                jboolean is_sensitive);
}