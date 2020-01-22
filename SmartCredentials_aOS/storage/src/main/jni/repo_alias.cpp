#include <jni.h>

#include <cstdio>

#include "repo_alias.h"

extern "C" jstring
Java_de_telekom_smartcredentials_storage_repositories_RepositoryAliasNative_alias(JNIEnv *env,
                                                                        jclass clazz,
                                                                        jboolean is_sensitive) {
    if (is_sensitive) {
        return env->NewStringUTF("bhvaullywm");
    } else {
        return env->NewStringUTF("kpqmfnkuwm");
    }
}