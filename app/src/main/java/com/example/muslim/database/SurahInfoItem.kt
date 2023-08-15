package com.example.muslim.database

data class SurahInfoItem(
    val count: Int,
    val index: String,
    val juz: List<Juz>,
    val pages: String,
    val place: String,
    val title: String,
    val titleAr: String,
    val type: String
)