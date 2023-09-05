package com.example.muslim.database.bookmark.dao.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.muslim.database.bookmark.SavedPage

@Dao
interface SavedPageDao {

    // insert data into quran_table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuran(quran_table:SavedPage) : Long
    // get all data from quran_table
    @Query("SELECT * FROM saved_page")
    fun getAllQuranLiveData(): LiveData<List<SavedPage>>
    // get all data from quran_table
    @Query("SELECT * FROM saved_page")
    fun getAllQuran(): List<SavedPage>

    @Delete
    fun deletePage(savedPage: SavedPage)

    // delete page by pageNumber
    @Query("DELETE FROM saved_page WHERE pageNumber = :pageNumber")
    fun deletePageByPageNumber(pageNumber: Int)

}