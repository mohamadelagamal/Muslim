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
        private val DATABASE_NAME = "quran-Database";

        private var myDataBase: QuranDataBase? = null;

        fun getInstance(context: Context): QuranDataBase {
            // single object from database (Singleton pattern)
            if (myDataBase == null) {
                myDataBase = Room.databaseBuilder(
                    context, QuranDataBase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()//ANR -> wait-kill
                    .build()
            }
            return myDataBase!!;
        }
    }
}