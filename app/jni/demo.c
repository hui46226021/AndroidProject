#include <string.h>
#include <jni.h>

jstring

Java_com_sh_shprojectdemo_MainActivity_getStrFromJNI(JNIEnv *env,jobject thiz) {

return (*env)->NewStringUTF(env, "I`m Str !");

}