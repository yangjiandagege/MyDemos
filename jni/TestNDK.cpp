#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <android/log.h>
#define LOG_TAG "TestNDK"
#define LOGI(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

static char s_string[] = "My god, I did it!";

jstring Java_com_example_test_ndk_MyJni_stringFromJNI( JNIEnv* env, jobject obj )
{
       LOGI("YangJian MyJNI is called!");
       return env->NewStringUTF(s_string);
}

#ifdef __cplusplus
}
#endif
