package com.example.muslim.database.bookmark.mydatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.dao.SavedPageDao

@Database(entities=[SavedPage::class], version=1, exportSchema = false)
abstract class QuranDataBase :RoomDatabase(){

    abstract fun savedPageDao(): SavedPageDao
    companion object{
        const val DATABASE_NAME = "quran.db"
        private var instance: QuranDataBase? = null
        fun getInstance(context: Context): QuranDataBase? {
            if (instance == null) {
                synchronized(QuranDataBase::class.java) {
                    if (instance == null) {
                        try {
                            instance = Room.databaseBuilder(
                                context.applicationContext,
                                QuranDataBase::class.java, DATABASE_NAME
                            )
                                .createFromAsset("quran/quran.db")
                                .build()
                        } catch (e: Exception) {
                            return null
                        }
                    }
                }
            }
            return instance
        }
    }
}