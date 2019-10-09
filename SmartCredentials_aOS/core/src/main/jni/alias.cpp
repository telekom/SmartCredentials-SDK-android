#include <jni.h>
#include <cstdio>
#include "alias.h"

extern "C" jstring Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias(JNIEnv *env,
                                                                                        jclass type) {
    return env->NewStringUTF("SmartCredentialsGeneratedKeyStore");
}

extern "C" jstring
Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias__Z(JNIEnv *env,
                                                                        jclass type,
                                                                        jboolean isSensitive) {
    if (isSensitive) {
        return env->NewStringUTF("bhvaullywm");
    } else {
        return env->NewStringUTF("kpqmfnkuwm");
    }
}

int exists(const char *fname) {
    FILE *file;
    if ((file = fopen(fname, "r"))) {
        fclose(file);
        return 1;
    }
    return 0;
}

extern "C"
JNIEXPORT jint JNICALL
Java_de_telekom_smartcredentials_core_rootdetector_RootDetectionNative_checkRooted(JNIEnv *env,
                                                                                   jobject instance,
                                                                                   jobjectArray pathArray) {
    int binariesFound = 0;

    int stringCount = (env)->GetArrayLength(pathArray);

    for (int i = 0; i < stringCount; i++) {
        auto string = (jstring) (env)->GetObjectArrayElement(pathArray, i);
        const char *pathString = (env)->GetStringUTFChars(string, nullptr);

        binariesFound += exists(pathString);

        (env)->ReleaseStringUTFChars(string, pathString);
    }

    return binariesFound > 0;
}