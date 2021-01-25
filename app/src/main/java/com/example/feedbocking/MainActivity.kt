package com.example.feedbocking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feedbocking.FileManager.Companion.readFile
import com.example.feedbocking.data.FeedDatabase
import com.example.feedbocking.model.Feed
import com.example.feedbocking.model.FeedData
import com.example.feedbocking.utils.BitmapManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val database by lazy { FeedDatabase.getDatabase(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val fileData: String = readFile(applicationContext, "data.txt")
        var obj = JSONObject(fileData)
        var json: JSONObject = obj.getJSONObject("data")
        val gson = Gson()
        val feedData: FeedData = gson.fromJson(json.toString(), FeedData::class.java)
        feedData.bitMapping=feedData.setBitMap(feedData.bitmap)
//        saveFeedData(feedData)
//        saveFeeds(feedData.feed)

        getTask()
    }

    private fun saveFeedData(feedData: FeedData) = lifecycleScope.launch {
        try {
            database.feedDataDao().insertFeedData(feedData)
        } catch (e: Exception) {
            var e = e.toString()
        }
    }

    private fun saveFeeds(feeds:List<Feed>) = lifecycleScope.launch{
        try {
            database.feedDao().insert(feeds)
        } catch (e: Exception) {
            var e = e.toString()
        }
    }

    private fun getTask() = lifecycleScope.launch (Dispatchers.Default) {
        try {
         var data=   database.feedDao().getFeeds();
         var dataFeedRelated=   database.feedDataDao().getFeedData();
            var bm: BitmapManager = BitmapManager();
            var ss:String= bm.isDomainExistInBitmap("facebook.com",dataFeedRelated);

        } catch (e: Exception) {
            var e = e.toString()
        }
    }

//    /**
//     * A native method that is implemented by the 'native-lib' native library,
//     * which is packaged with this application.
//     */
//    external fun djb2(obj: String?): String

//    /**
//     * A native method that is implemented by the 'native-lib' native library,
//     * which is packaged with this application.
//     */
//    external fun sdbm(obj: String?): String
}


//Java_com_example_feedbocking_MainActivity_djb2(
//JNIEnv* env,
//jobject /* this */,
//jstring obj) {
//
//    const char *domain = env->GetStringUTFChars(obj, 0);
//    const char *domainsdbm = env->GetStringUTFChars(obj, 0);
//
//
//    unsigned unsigned int hash = 5381;
//    unsigned int c;
//
//    while (c = *domain++)
//        hash = ((hash << 5) + hash)  ^ c; /* hash * 33 + c */
//
//
//
//    unsigned unsigned int sdhash = 0;
//    unsigned int c1;
//
//    while (c1 = *domainsdbm++)
//        sdhash = c1 + (sdhash << 6) + (sdhash << 16) - sdhash;
//
//
//    std::string result = std::to_string(hash) + "@" + std::to_string(sdhash);
//
//
//    return env->NewStringUTF(result.c_str());
//}