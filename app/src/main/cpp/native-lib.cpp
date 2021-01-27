#include <jni.h>
#include <string>
#include <math.h>


unsigned int sdbmHash(const char *domain) {
    unsigned unsigned int hash = 0;
    unsigned int c1;

    while (c1 = *domain++) {
        hash = c1 + (hash << 6) + (hash << 16) - hash;
    }
    return hash;
}

unsigned int djb2Hash(const char *domain) {

    unsigned unsigned int djb2Hash = 5381;
    unsigned int c;

    while (c = *domain++) {
        djb2Hash = ((djb2Hash << 5) + djb2Hash) ^ c;
    }
    return djb2Hash;
}

long getBit(long num, long index) {
    return (num >> index) & 1;
}


long getDJB2Bit(const char *domain, int size, jlong *elements) {

    long djb2Mode = djb2Hash(domain) % size;
    int djb2ArrayIndex = (int) floor(djb2Mode / 32);
    long djb2WordBit = (djb2Mode % 32);
    jlong djb2Number = elements[djb2ArrayIndex];
    return getBit(djb2Number, djb2WordBit);
}

long getSDBMBit(const char *domain, int size, jlong *elements) {

    long sdbmMode = sdbmHash(domain) % size;
    int sdbmArrayIndex = (int) floor(sdbmMode / 32);
    long sdbmWordBit = (sdbmMode % 32);
    long sdbmNumber = elements[sdbmArrayIndex];
    return getBit(sdbmNumber, sdbmWordBit);
}


extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_feedbocking_utils_BitmapManager_isDomainExistBM(
        JNIEnv *env,
        jobject /* this */,
        jstring obj,
        jlongArray array) {

    const char *domain = env->GetStringUTFChars(obj, 0);
    const char *domainsdbm = env->GetStringUTFChars(obj, 0);
    jlong *elements = env->GetLongArrayElements(array, 0);


    int count = env->GetArrayLength(array);
    int size = count * 32;

    env->ReleaseLongArrayElements(array, elements, NULL);
    env->ReleaseStringChars(obj, reinterpret_cast<const jchar *>(domainsdbm));
    env->ReleaseStringChars(obj, reinterpret_cast<const jchar *>(domain));

    return (getDJB2Bit(domain, size, elements) == 1 && getSDBMBit(domainsdbm, size, elements) == 1);


}


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







