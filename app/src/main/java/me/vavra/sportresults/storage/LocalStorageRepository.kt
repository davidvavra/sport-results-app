package me.vavra.sportresults.storage

import androidx.room.Room
import me.vavra.sportresults.App
import me.vavra.sportresults.model.SportResult

object LocalStorageRepository {
    private val db = Room.databaseBuilder(
        App.context,
        AppDatabase::class.java,
        "sport-results"
    ).build()
    private val dao = db.sportResultDao()

    suspend fun get(): List<SportResult> {
        return dao.getAll().map {
            SportResult(
                name = it.name,
                place = it.place,
                durationMinutes = it.durationMinutes,
                remote = false,
                timestamp = it.timestamp
            )
        }
    }

    suspend fun new(sportResult: SportResult) {
        dao.insert(
            SportResultEntity(
                name = sportResult.name,
                place = sportResult.place,
                durationMinutes = sportResult.durationMinutes,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}