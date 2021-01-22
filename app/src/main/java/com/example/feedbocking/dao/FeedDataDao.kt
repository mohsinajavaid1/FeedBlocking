package com.example.feedbocking.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedbocking.model.Feed
import com.example.feedbocking.model.FeedData

@Dao
interface FeedDataDao {

    @Query("SELECT * FROM feed_data ")
    suspend fun getFeedData(): FeedData


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedData(feedData: FeedData)

    @Query("DELETE FROM feed")
    suspend fun deleteAll()
}