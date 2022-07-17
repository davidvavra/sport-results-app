package me.vavra.sportresults.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import me.vavra.sportresults.model.SportResult
import org.junit.Assert
import org.junit.Test

class RemoteRepositoryTests {

    private val subject = LocalRepositoryImpl(MockDao())

    @Test
    fun `cache contains sport results WHEN they are observed`() {
        runBlocking {
            // When
            val actual = subject.observe().first()
            // Then
            val expected = listOf(
                SportResult(
                    name = "Swimming",
                    place = "Praha",
                    durationMinutes = 45,
                    timestamp = 42,
                    remote = false
                )
            )
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
                    remote = false
                )
            )
            // Then
            val expected = listOf(
                SportResult(
                    name = "Swimming",
                    place = "Praha",
                    durationMinutes = 45,
                    timestamp = 42,
                    remote = false
                ),
                SportResult(
                    name = "Running",
                    place = "Brno",
                    durationMinutes = 60,
                    timestamp = 43,
                    remote = false
                )
            )
            val actual = subject.observe().first()
            Assert.assertEquals(expected, actual)
        }
    }
}

class MockDao : SportResultDao {

    private val mockData = mutableListOf(
        SportResultEntity(
            name = "Swimming",
            place = "Praha",
            durationMinutes = 45,
            timestamp = 42
        )
    )

    override fun observeAll(): Flow<List<SportResultEntity>> {
        return flowOf(mockData)
    }

    override suspend fun insert(sportResult: SportResultEntity) {
        mockData.add(sportResult)
    }

}