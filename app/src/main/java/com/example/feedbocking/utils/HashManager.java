package com.example.feedbocking.utils;

import android.util.Log;

public class HashManager {



    public static long SDBMHash(String str)
    {
        long hash = 0;

        for(int i = 0; i < str.length(); i++)
        {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }

        return hash;
    }
    /* End Of SDBM Hash Function */


    public static long DJBHash(String str)
    {
        long hash = 5381;
        long mod= (1L << 32)-1;

        for(int i = 0; i < str.length(); i++)
        {
            Log.d("before Hash", "DJBHash:"+hash+ "index:"+i);
            hash = ((((hash << 5)%mod) + hash) % mod) ^ str.charAt(i);
            Log.d("After Hash", "DJBHash:"+hash);
        }

        return hash;
    }
}

