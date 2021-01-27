package com.example.feedbocking

import android.content.Context
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class FileManager{

    companion object {

        fun readFile(context: Context, fileName: String): String {
            val json_string = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
            return json_string
        }


        fun readFileDomain(context: Context, fileName: String): MutableList<String> {
                var listOfDomain: MutableList<String> = mutableListOf()
                val reader: BufferedReader
                reader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
                var line:String? = reader.readLine();

            while (line != null) {
                   var arr:List<String> = line.split(",")
                    listOfDomain.add(arr[1])

                line = reader.readLine()
                if(line==null){
                        break
                    }
                }

            return listOfDomain
        }
    }
}