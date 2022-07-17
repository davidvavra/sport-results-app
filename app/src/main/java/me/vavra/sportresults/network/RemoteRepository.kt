package me.vavra.sportresults.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.vavra.sportresults.model.SportResult

interface RemoteRepository {
    fun observe(): Flow<List<SportResult>>
    suspend fun new(sportResult: SportResult)
    suspend fun load()
}

class RemoteRepositoryImpl(private val remoteService: RemoteService) : RemoteRepository {

    private val cache = MutableStateFlow<List<SportResult>>(listOf())

    override fun observe(): Flow<List<SportResult>> {
        return cache
    }

    override suspend fun new(sportResult: SportResult) {
        remoteService.postSportResult(
            SportResultJson(
                name = sportResult.name,
                place = sportResult.place,
                durationMinutes = sportResult.durationMinutes,
                timestamp = sportResult.timestamp
            )
        )
        val newCacheValue = cache.value + sportResult
        cache.emit(newCacheValue)
    }

    override suspend fun load() {
        val sportResults = remoteService.getSportResults().values.map {
            SportResult(
                name = it.name,
                place = it.place,
                durationMinutes = it.durationMinutes,
                timestamp = it.timestamp,
                remote = true
            )
        }
        cache.emit(sportResults)
    }
}