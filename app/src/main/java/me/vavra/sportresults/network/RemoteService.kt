package me.vavra.sportresults.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RemoteService {
    @GET("/sport-results.json")
    suspend fun getSportResults(): Map<String, SportResultJson>

    @POST("/sport-results.json")
    suspend fun postSportResult(@Body sportResultJson: SportResultJson)

}