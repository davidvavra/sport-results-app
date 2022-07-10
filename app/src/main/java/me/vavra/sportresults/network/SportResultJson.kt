package me.vavra.sportresults.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportResultJson(
    val name: String,
    val place: String,
    val durationMinutes: Int,
    val timestamp: Long
)
