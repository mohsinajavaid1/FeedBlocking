#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_feedbocking_utils_BitmapManager_djb2(
        JNIEnv* env,
        jobject /* this */,
        jstring obj) {

    const char *domain = env->GetStringUTFChars(obj, 0);

    unsigned unsigned int hash = 5381;
    unsigned int c;

    while (c = *domain++) {
        hash = ((hash << 5) + hash) ^ c; /* hash * 33 + c */
    }


    std::string result = "" + std::to_string(hash);
    return env->NewStringUTF(result.c_str());

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_feedbocking_utils_BitmapManager_sdbm(
        JNIEnv* env,
        jobject /* this */,
        jstring obj) {

    const char *domainsdbm = env->GetStringUTFChars(obj, 0);

    unsigned unsigned int sdhash = 0;
    unsigned int c1;

    while (c1 = *domainsdbm++) {
        sdhash = c1 + (sdhash << 6) + (sdhash << 16) - sdhash;
    }

    std::string result = "" + std::to_string(sdhash);
    return env->NewStringUTF(result.c_str());
}



