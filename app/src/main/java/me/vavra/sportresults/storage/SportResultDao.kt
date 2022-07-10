package me.vavra.sportresults.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SportResultDao {
    @Query("SELECT * FROM sport_results")
    suspend fun getAll(): List<SportResultEntity>

    @Insert
    suspend fun insert(sportResult: SportResultEntity)
}