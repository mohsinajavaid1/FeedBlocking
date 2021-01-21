package com.example.feedbocking.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedbocking.model.Feed
import com.example.feedbocking.model.FeedData

interface FeedDataDao {

    @Query("SELECT * FROM feed_data ")
    fun getFeedData(): FeedData


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFeedData(feedData: FeedData)

    @Query("DELETE FROM feed")
    suspend fun deleteAll()
}