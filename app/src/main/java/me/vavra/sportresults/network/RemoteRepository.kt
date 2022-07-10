package me.vavra.sportresults.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.vavra.sportresults.model.SportResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RemoteRepository {
    private val remoteService = Retrofit.Builder()
        .baseUrl("https://sport-results-654a4-default-rtdb.europe-west1.firebasedatabase.app")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(RemoteService::class.java)

    private val cache = MutableStateFlow<List<SportResult>>(listOf())

    fun observe(): Flow<List<SportResult>> {
        return cache
    }

    suspend fun new(sportResult: SportResult) {
        remoteService.postSportResult(
            SportResultJson(
                name = sportResult.name,
                place = sportResult.place,
                durationMinutes = sportResult.durationMinutes,
                timestamp = System.currentTimeMillis()
            )
        )
        val newCacheValue = cache.value + sportResult.copy(timestamp = System.currentTimeMillis())
        cache.emit(newCacheValue)
    }

    suspend fun load() {
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