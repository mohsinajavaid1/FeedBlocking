#include <jni.h>
#include <string>
#include <math.h>


jlong modifyBit(long number, long index, long i);

void updateBit(jlong *pInt, jlong number,jlong size);

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

extern "C" JNIEXPORT jlongArray JNICALL
Java_com_example_feedbocking_utils_BitmapManager_processDeltaBM(
        JNIEnv *env,
        jobject /* this */,
        jlongArray array) {


    jlong *elements = env->GetLongArrayElements(array, 0);


    int length = env->GetArrayLength(array);
    long size=length*32;
    for (int i = 0; i < length; i++) {
        jlong number = (jlong) (env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(array),i));
        updateBit(elements, number,size);
    }

    env->ReleaseLongArrayElements(array, elements, NULL);

    return array;
}

void updateBit(jlong *array, jlong element,jlong size) {
    int arrayIndex = (int) floor(element / size);
    long bitIndex = (arrayIndex % size);
    long number = array[arrayIndex];
    //number |= number << bitIndex-1;
    array[arrayIndex] = modifyBit(number, bitIndex, 1);;
}




jlong modifyBit(long n, long p, long b) {
    int mask = 1 << p;
    return (n & ~mask) | ((b << p) & mask);
}

extern "C" JNIEXPORT jlongArray JNICALL
Java_com_example_feedbocking_utils_BitmapManager_createBitmap(
        JNIEnv *env,
        jobject /* this */,
        jstring obj,
        jlongArray array){

    const char *domain = env->GetStringUTFChars(obj, 0);
    jlong *elements = env->GetLongArrayElements(array, 0);
    int length = env->GetArrayLength(array);
    long size=length*32;

    //djb2
    long djb2Mode = djb2Hash(domain) % size;
    int djb2ArrayIndex = (int) floor(djb2Mode / 32);
    long djb2WordBit = (djb2Mode % 32);
    jlong djb2Number = elements[djb2ArrayIndex];

    elements[djb2ArrayIndex] = modifyBit(djb2Number, djb2WordBit, 1);


    long sdbmMode = sdbmHash(domain) % size;
    int sdbmArrayIndex = (int) floor(sdbmMode / 32);
    long sdbmWordBit = (sdbmMode % 32);
    long sdbmNumber = elements[sdbmArrayIndex];

    elements[sdbmArrayIndex] = modifyBit(sdbmNumber, sdbmWordBit, 1);

    env->ReleaseLongArrayElements(array, elements, NULL);

    return array;
}









