package com.example.muslim.model.quran

data class SurahInfoItem(
    val count: Int? = null,
    val index: String? = null,
    val juz: List<Juz>? = null,
    val pages: String? = null,
    val place: String? = null,
    val title: String? = null,
    val titleAr: String? = null,
    val type: String? = null
)