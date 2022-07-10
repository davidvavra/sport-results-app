package me.vavra.sportresults.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SportResultDao {
    @Query("SELECT * FROM sport_results")
    fun observeAll(): Flow<List<SportResultEntity>>

    @Insert
    suspend fun insert(sportResult: SportResultEntity)
}