package me.vavra.sportresults.storage

import android.content.Context
import androidx.room.Room
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.vavra.sportresults.model.SportResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(@ApplicationContext context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "sport-results"
    ).build()
    private val dao = db.sportResultDao()

    fun observe(): Flow<List<SportResult>> {
        return dao.observeAll().map { entities ->
            entities.map {
                SportResult(
                    name = it.name,
                    place = it.place,
                    durationMinutes = it.durationMinutes,
                    remote = false,
                    timestamp = it.timestamp
                )
            }
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