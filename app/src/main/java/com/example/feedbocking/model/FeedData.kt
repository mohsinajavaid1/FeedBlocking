package com.example.feedbocking.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName

@Entity(tableName = "feed_data")
class FeedData(
    @Ignore @SerializedName("bit_mapping") val bitmap: List<Int>,
    var bitMapping: String,
    var lastSyncDomainPointer: Int,
    @Ignore val feed: List<Feed>,
    @Ignore var gson: Gson,@PrimaryKey(autoGenerate = true)
    var id: Int = 0

) {
    constructor() : this(ArrayList<Int>(), "", 0, ArrayList<Feed>(), GsonBuilder().create())

    init {
        gson = GsonBuilder().create()

    }

    fun setBitMap(bitmap: List<Int>): String = gson.toJson(bitmap)

    fun getBitMap(bitMapping: String): List<Int> =
        gson.fromJson(bitMapping, Array<Int>::class.java).toList()


}