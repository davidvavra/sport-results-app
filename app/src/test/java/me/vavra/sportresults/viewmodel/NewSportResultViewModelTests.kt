package me.vavra.sportresults.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.vavra.sportresults.model.SavingState
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.network.RemoteRepository
import me.vavra.sportresults.storage.LocalRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NewSportResultViewModelTests {

    lateinit var subject: NewSportResultViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(newSingleThreadContext("UI thread"))
        // When
        subject = NewSportResultViewModel(MockRemoteRepository(), MockLocalRepository())
        Thread.sleep(100)
    }

    @Test
    fun `invalid name`() {
        // When
        subject.nameChanged("")
        // Then
        val actual = subject.state.nameValid
        val expected = false
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `invalid place`() {
        // When
        subject.placeChanged("")
        // Then
        val actual = subject.state.placeValid
        val expected = false
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `invalid duration`() {
        // When
        subject.durationChanged("-1")
        // Then
        val actual = subject.state.durationValid
        val expected = false
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `saving is successful WHEN everything is valid`() = runTest {
        // When
        subject.nameChanged("Swimming")
        subject.placeChanged("Praha")
        subject.durationChanged("45")
        subject.save()
        Thread.sleep(100)
        // Then
        val actual = subject.state.savingState
        val expected = SavingState.SUCCESS
        Assert.assertEquals(expected, actual)
    }

    class MockRemoteRepository : RemoteRepository {
        override fun observe(): Flow<List<SportResult>> {
            TODO("Not yet implemented")
        }

        override suspend fun new(sportResult: SportResult) {
        }

        override suspend fun load() {
            TODO("Not yet implemented")
        }
    }

    class MockLocalRepository : LocalRepository {
        override fun observe(): Flow<List<SportResult>> {
            TODO("Not yet implemented")
        }

        override suspend fun new(sportResult: SportResult) {
        }
    }
}

