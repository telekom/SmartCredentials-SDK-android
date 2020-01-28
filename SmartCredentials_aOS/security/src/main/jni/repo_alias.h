extern "C" {

#include <jni.h>

jstring
Java_de_telekom_smartcredentials_security_repositories_RepositoryAliasNative_aliasSensitive(
        JNIEnv *env,
        jclass clazz);

jstring
Java_de_telekom_smartcredentials_security_repositories_RepositoryAliasNative_aliasNonSensitive(
        JNIEnv *env,
        jclass clazz);
}