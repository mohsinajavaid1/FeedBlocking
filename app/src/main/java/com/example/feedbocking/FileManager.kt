package com.example.feedbocking

import android.content.Context

class FileManager{

    companion object {

        fun readFile(context: Context, fileName: String): String {
            val json_string = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            return json_string
        }
    }
}