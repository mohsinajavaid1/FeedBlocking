package com.example.feedbocking.utils;

import androidx.loader.content.AsyncTaskLoader;

import com.example.feedbocking.data.FeedDatabase;
import com.example.feedbocking.model.FeedData;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class BitmapManager {

    static {
        System.loadLibrary("native-lib");
    }



    public boolean isDomainExistInBitmap(String url, long[] bitmap) {

        return  isDomainExistBM(url,bitmap);

    }
    public long[] CreateNewBitmap(String url, long[] bitmap) {

        return  createBitmap(url,bitmap);

    }
    long getBit(long num, long index) {
        return (num >> index) & 1;
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native boolean isDomainExistBM(String obj,long[] arr);

    public native List<Long> processDeltaBM(long[] arr);

    public native long[] createBitmap(String url, long[] bitmap);

}
