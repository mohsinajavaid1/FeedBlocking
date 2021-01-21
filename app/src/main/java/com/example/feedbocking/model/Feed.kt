package com.example.feedbocking.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "feed")
class Feed(@SerializedName("h") val host: String, @SerializedName("t") val type: String) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0

}
