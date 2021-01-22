package com.example.feedbocking

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.feedbocking.FileManager.Companion.readFile
import com.example.feedbocking.model.FeedData
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val fileData:String=readFile(applicationContext,"data.txt")
        var obj = JSONObject(fileData)
        var json:JSONObject=obj.getJSONObject("data")  
        val gson = Gson()
        val feedData: FeedData = gson.fromJson(json.toString(), FeedData::class.java)
        val num:Int=5;
    }

}