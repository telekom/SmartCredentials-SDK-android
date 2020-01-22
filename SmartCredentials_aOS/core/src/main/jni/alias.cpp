#include <jni.h>

#include <cstdio>

#include "alias.h"

extern "C" jstring Java_de_telekom_smartcredentials_core_repositories_AliasNative_alias(JNIEnv *env,
                                                                                        jclass clazz)
{
    return env->NewStringUTF("SmartCredentialsGeneratedKeyStore");
}

int exists(const char *fname)
{
    FILE *file;
    if ((file = fopen(fname, "re")))
    {
        fclose(file);
        return 1;
    }
    return 0;
}

extern "C" int Java_de_telekom_smartcredentials_core_rootdetector_RootDetectionNative_checkRooted( JNIEnv* env, jobject thiz , jobjectArray pathsArray )
{
    int binariesFound = 0;
    int stringCount = (env)->GetArrayLength(pathsArray);
    for (int i=0; i<stringCount; i++) {
        auto string = (jstring) (env)->GetObjectArrayElement(pathsArray, i);
        const char *pathString = (env)->GetStringUTFChars(string, nullptr);
        binariesFound+=exists(pathString);
        (env)->ReleaseStringUTFChars(string, pathString);
    }
    return binariesFound>0;
}
