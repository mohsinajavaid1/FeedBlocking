#include <jni.h>
#include <string>
#include <math.h>

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_example_feedbocking_utils_BitmapManager_djb2(
//        JNIEnv* env,
//        jobject /* this */,
//        jstring obj) {
//
//    const char *domain = env->GetStringUTFChars(obj, 0);
//
//    unsigned unsigned int hash = 5381;
//    unsigned int c;
//
//    while (c = *domain++) {
//        hash = ((hash << 5) + hash) ^ c; /* hash * 33 + c */
//    }
//
//
//    std::string result = "" + std::to_string(hash);
//    return env->NewStringUTF(result.c_str());
//
//}

long getBit(long num, long index) {
    return (num >> index) & 1;
}
extern "C" JNIEXPORT bool JNICALL
Java_com_example_feedbocking_utils_BitmapManager_isDomainExistBM(
        JNIEnv* env,
        jobject /* this */,
        jstring obj,
        jlongArray array) {

    const char *domain = env->GetStringUTFChars(obj, 0);
    const char *domainsdbm = env->GetStringUTFChars(obj, 0);
    jlong *elements = env->GetLongArrayElements(array, 0);


//        int value = elements[2];
//        int count = env->GetArrayLength(array);


    unsigned unsigned int djb2Hash = 5381;
    unsigned int c;

    while (c = *domain++) {
        djb2Hash = ((djb2Hash << 5) + djb2Hash) ^ c;
    }

    unsigned unsigned int sdbmHash = 0;
    unsigned int c1;

    while (c1 = *domainsdbm++) {
    sdbmHash = c1 + (sdbmHash << 6) + (sdbmHash << 16) - sdbmHash;
    }

    int count = env->GetArrayLength(array);
    int size=count * 32;

        //First we work for djb2
        long djb2Mode = djb2Hash % size;
        int djb2ArrayIndex = (int) floor(djb2Mode / 32);
        long djb2WordBit = (djb2Mode % 32);
        long djb2Number = elements[djb2ArrayIndex];
        long djb2Bit = getBit(djb2Number, djb2WordBit);


        long sdbmMode = sdbmHash % size;
        int sdbmArrayIndex = (int) floor(sdbmMode / 32);//1
        long sdbmWordBit = (sdbmMode % 32);
        long sdbmNumber = elements[sdbmArrayIndex];
        long sdbmBit = getBit(sdbmNumber, sdbmWordBit);

        if(djb2Bit==1 && sdbmBit==1){
            return true;
        }else{
            return false;
        }

//        std::string result = std::to_string(djb2Mode);
//        return env->NewStringUTF(result.c_str());
//        //return env->NewStringUTF("asasad");
}





