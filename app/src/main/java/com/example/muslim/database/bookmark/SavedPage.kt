package com.example.muslim.database.bookmark

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_page")
data class SavedPage (
    @PrimaryKey()
    val pageNumber : Int
        )