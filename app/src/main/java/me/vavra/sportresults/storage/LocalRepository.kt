package me.vavra.sportresults.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.vavra.sportresults.model.SportResult

interface LocalRepository {
    fun observe(): Flow<List<SportResult>>
    suspend fun new(sportResult: SportResult)
}

class LocalRepositoryImpl(private val dao: SportResultDao) : LocalRepository {

    override fun observe(): Flow<List<SportResult>> {
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

    override suspend fun new(sportResult: SportResult) {
        dao.insert(
            SportResultEntity(
                name = sportResult.name,
                place = sportResult.place,
                durationMinutes = sportResult.durationMinutes,
                timestamp = sportResult.timestamp
            )
        )
    }
}