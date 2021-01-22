package com.example.feedbocking.utils

class HashUtils {

    companion object {
        fun DJB2hash(str: String): Int {
            var hash = 0
            for (element in str) {
                hash = element.toInt() + ((hash shl 5) - hash)
            }
            return hash
        }


        fun SDBMHash(str: String): Long {
            var hash: Long = 0
            for (element in str) {
                hash = element.toLong() + (hash shl 6) + (hash shl 16) - hash
            }
            return hash
        }
    }
}