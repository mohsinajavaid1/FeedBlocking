package com.example.feedbocking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.feedbocking.dao.FeedDao
import com.example.feedbocking.dao.FeedDataDao
import com.example.feedbocking.model.Feed
import com.example.feedbocking.model.FeedData


@Database(entities = [Feed::class, FeedData::class], version = 1, exportSchema = false)
public abstract class FeedDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao
    abstract fun feedDataDao(): FeedDataDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FeedDatabase? = null

        fun getDatabase(context: Context): FeedDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FeedDatabase::class.java,
                    "feed_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}


