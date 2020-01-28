#include <jni.h>

#include <cstdio>

#include "repo_alias.h"

extern "C" jstring
Java_de_telekom_smartcredentials_security_repositories_RepositoryAliasNative_aliasSensitive(
        JNIEnv *env,
        jclass clazz) {
    return env->NewStringUTF("bhvaullywm");
}

extern "C" jstring
Java_de_telekom_smartcredentials_security_repositories_RepositoryAliasNative_aliasNonSensitive(
        JNIEnv *env,
        jclass clazz) {

    return env->NewStringUTF("kpqmfnkuwm");
}