package com.example.muslim.database.bookmark

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_page")
data class SavedPage (
    @PrimaryKey()
    val pageNumber : Int, // page number
//    @PrimaryKey(autoGenerate = true)// auto increment id
  //  var id: Int = 0, // id of page (auto increment)
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB) // typeAffinity = ColumnInfo.BLOB -> for image data (byte array)
    //var data :ByteArray? = null // image data (byte array) from page (bitmap)
        ){}