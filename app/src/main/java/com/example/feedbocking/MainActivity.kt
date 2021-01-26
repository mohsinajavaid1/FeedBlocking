package com.example.feedbocking

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.example.feedbocking.FileManager.Companion.readFile
import com.example.feedbocking.data.FeedDatabase
import com.example.feedbocking.model.Feed
import com.example.feedbocking.model.FeedData
import com.example.feedbocking.utils.HashManager
import com.example.feedbocking.utils.HashUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

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

        var djb2=HashManager.DJBHash("facebook.com")
        Log.d("DjB2",djb2.toString())
        var smdb= HashManager.SDBMHash("facebook.com")
        Log.d("SMDB",smdb.toString())
        var s="google.com"
/*       saveFeedData(feedData)
        saveFeeds(feedData.feed)*/
     //   getTask()


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
            var a=data.size;
        } catch (e: Exception) {
            var e = e.toString()
        }
    }

}