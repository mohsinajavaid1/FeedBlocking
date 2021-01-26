package com.example.feedbocking.utils;

import androidx.loader.content.AsyncTaskLoader;

import com.example.feedbocking.data.FeedDatabase;
import com.example.feedbocking.model.FeedData;

import java.util.List;

public class BitmapManager {

    static  {
            System.loadLibrary("native-lib");
    }
    public  boolean isDomainExistInBitmap(String url, List<Long> bitmap){
        int size= bitmap.size()*32;

        long djb2Hash=Long.parseLong(djb2(url));
        long djb2Mode=djb2Hash%size;
        int djb2ArrayIndex= (int)Math.floor(djb2Mode/32);//1
        long djb2WordBit= (djb2Mode%32);

        long djb2Number=bitmap.get((int)djb2ArrayIndex);

        long djb2Bit =getBit(djb2Number,djb2WordBit);
       // long djb2Bit =getBit(djb2Number,17);

        long sdbmHash=Long.parseLong(sdbm(url));
        long sdbmMode=sdbmHash%size;
        int sdbmArrayIndex= (int)Math.floor(sdbmMode/32);//1
        long sdbmWordBit= (sdbmMode%32);
        long sdbmNumber=bitmap.get((int)sdbmArrayIndex);
        long sdbmBit =getBit(sdbmNumber,sdbmWordBit);

//        String ans="";
//        for(int a=0;a<64;a++){
//            ans+=getBit(100,a);
//        }

        if(sdbmBit==1 && djb2Bit==1){
            return true;
        }else{
            return false;
        }

    }

    long getBit(long num, long index) {
        return (num >> index) & 1;
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public  native String djb2(String obj);

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public  native String sdbm(String obj);
}
