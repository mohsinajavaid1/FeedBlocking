package com.example.feedbocking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.feedbocking.FileManager.Companion.readFile
import com.example.feedbocking.FileManager.Companion.readFileDomain
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
import java.util.Arrays.stream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val database by lazy { FeedDatabase.getDatabase(this) }
    val gson = Gson()
    var truePositive = 0;
    var falsePositive = 0;
    var present = 0
    lateinit var domainData: MutableList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        domainData = readFileDomain(applicationContext, "alexanew.txt")


        val fileData: String = readFile(applicationContext, "data.txt")
        var obj = JSONObject(fileData)
        var json: JSONObject = obj.getJSONObject("data")
        val feedData: FeedData = gson.fromJson(json.toString(), FeedData::class.java)
        feedData.bitMapping = feedData.setBitMap(feedData.bitmap)



        getTask()
    }


    private fun getTask() = lifecycleScope.launch(Dispatchers.Default) {
        try {
            var data: List<Feed> = database.feedDao().getFeeds();
            var dataFeedRelated: FeedData = database.feedDataDao().getFeedData();
            var bitmap = dataFeedRelated.getBitMap(dataFeedRelated.bitMapping)
            var arr = bitmap.toLongArray()
            var bm: BitmapManager = BitmapManager();
            var startTime = System.currentTimeMillis()
            Log.e("Time", System.currentTimeMillis().toString())
            for (item in domainData) {
                var host: String = item
                if (item.startsWith("www.")) {
                    host = host.replaceFirst("www.", "")
                }

                var isExist = bm.isDomainExistInBitmap2(host, arr)
                if (isExist) {

                    var isMalicious = checkDomainFromDb(host)
                    if (isMalicious) present += 1
                    else falsePositive += 1
                } else {
                    truePositive += 1;
                    Log.e("DataSlashnext", item)
                }


            }
            var endTime = System.currentTimeMillis()

            var elapse = startTime - endTime
            Log.e("Time After Bitmap", endTime.toString())
            Log.e("Time Difference", elapse.toString())
            Log.e("DataSlashnext", "End")

        } catch (e: Exception) {
            var e = e.toString()
        }
    }

    private fun checkDomainFromDb(s: String): Boolean = database.feedDao().getFeedByHost(s) != null

    private fun saveFeedData(feedData: FeedData) = lifecycleScope.launch {
        try {
            database.feedDataDao().insertFeedData(feedData)
        } catch (e: Exception) {
            var e = e.toString()
        }
    }

    private fun saveFeeds(feeds: List<Feed>) = lifecycleScope.launch {
        try {
            database.feedDao().insert(feeds)
        } catch (e: Exception) {
            var e = e.toString()
        }
    }

}





