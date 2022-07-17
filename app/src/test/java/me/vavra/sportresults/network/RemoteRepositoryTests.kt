package me.vavra.sportresults.network

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.vavra.sportresults.model.SportResult
import org.junit.Assert
import org.junit.Test

class RemoteRepositoryTests {

    private val subject = RemoteRepositoryImpl(MockRemoteService())

    @Test
    fun `cache contains sport results WHEN they are loaded`() {
        runBlocking {
            // When
            subject.load()
            // Then
            val expected = listOf(
                SportResult(
                    name = "Swimming",
                    place = "Praha",
                    durationMinutes = 45,
                    timestamp = 42,
                    remote = true
                )
            )
            val actual = subject.observe().first()
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `cache contains new sport result WHEN it is added`() {
        runBlocking {
            // When
            subject.new(
                SportResult(
                    name = "Running",
                    place = "Brno",
                    durationMinutes = 60,
                    timestamp = 43,
                    remote = true
                )
            )
            // Then
            val expected = listOf(
                SportResult(
                    name = "Running",
                    place = "Brno",
                    durationMinutes = 60,
                    timestamp = 43,
                    remote = true
                )
            )
            val actual = subject.observe().first()
            Assert.assertEquals(expected, actual)
        }
    }
}

class MockRemoteService : RemoteService {

    private val mockData = mutableMapOf(
        "id1" to SportResultJson(
            name = "Swimming",
            place = "Praha",
            durationMinutes = 45,
            timestamp = 42
        )
    )

    override suspend fun getSportResults(): Map<String, SportResultJson> {
        return mockData
    }

    override suspend fun postSportResult(sportResultJson: SportResultJson) {
        mockData["id" + System.currentTimeMillis()] = sportResultJson
    }

}