package com.example.feedbocking

import java.math.BigInteger

class BitMap {

    fun Int.to32bitString(): String =

        Integer.toBinaryString(this).padStart(Int.SIZE_BITS, '0')

    fun String.changeBitAt(index: Int): String {


        val chars = String().toCharArray()
        chars[index] = '1'
        return String(chars)
    }

}