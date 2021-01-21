package com.example.feedbocking.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.feedbocking.model.Feed

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed ")
    fun getFeeds(): List<Feed>

    @Query("SELECT * FROM feed where host=:h")
    fun getFeedByHost(h:String): Feed

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(feed: List<Feed>)

    @Query("DELETE FROM feed")
    suspend fun deleteAll()
}