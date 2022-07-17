package me.vavra.sportresults.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.network.RemoteRepository
import me.vavra.sportresults.storage.LocalRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SportResultsViewModelTests {

    lateinit var subject: SportResultsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
        // When
        subject = SportResultsViewModel(MockRemoteRepository(), MockLocalRepository())
        Thread.sleep(100)
    }

    @Test
    fun `local and remote results are merged and sorted WHEN initialized`() = runTest {
        // Then
        val actual = subject.state.sportResults
        val expected = listOf(
            SportResult(
                name = "Running",
                place = "Brno",
                durationMinutes = 60,
                timestamp = 43,
                remote = false
            ), SportResult(
                name = "Swimming",
                place = "Praha",
                durationMinutes = 45,
                timestamp = 42,
                remote = true
            )
        )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `show only remote results WHEN local turned off`() = runTest {
        // When
        subject.changeShowLocal()
        // Then
        val actual = subject.state.sportResults
        val expected = listOf(
            SportResult(
                name = "Swimming",
                place = "Praha",
                durationMinutes = 45,
                timestamp = 42,
                remote = true
            )
        )
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `show only local results WHEN remote turned off`() = runTest {
        // When
        subject.changeShowRemote()
        // Then
        val actual = subject.state.sportResults
        val expected = listOf(
            SportResult(
                name = "Running",
                place = "Brno",
                durationMinutes = 60,
                timestamp = 43,
                remote = false
            )
        )
        Assert.assertEquals(expected, actual)
    }

    class MockRemoteRepository : RemoteRepository {
        override fun observe(): Flow<List<SportResult>> {
            return flowOf(
                listOf(
                    SportResult(
                        name = "Swimming",
                        place = "Praha",
                        durationMinutes = 45,
                        timestamp = 42,
                        remote = true
                    )
                )
            )
        }

        override suspend fun new(sportResult: SportResult) {
            TODO("Not yet implemented")
        }

        override suspend fun load() {
            TODO("Not yet implemented")
        }
    }

    class MockLocalRepository : LocalRepository {
        override fun observe(): Flow<List<SportResult>> {
            return flowOf(
                listOf(
                    SportResult(
                        name = "Running",
                        place = "Brno",
                        durationMinutes = 60,
                        timestamp = 43,
                        remote = false
                    )
                )
            )
        }

        override suspend fun new(sportResult: SportResult) {
            TODO("Not yet implemented")
        }
    }
}

