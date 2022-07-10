package me.vavra.sportresults.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sport_results")
data class SportResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val place: String,
    @ColumnInfo(name = "duration_minutes") val durationMinutes: Int,
    val timestamp: Long
)