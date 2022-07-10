package me.vavra.sportresults.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.model.SportResultsState
import me.vavra.sportresults.network.RemoteRepository
import me.vavra.sportresults.storage.LocalRepository

class SportResultsViewModel : ViewModel() {
    var state by mutableStateOf(
        SportResultsState(
            sportResults = listOf(),
            showRemote = true,
            showLocal = true
        )
    )
        private set

    private var allSportResults = listOf<SportResult>()

    init {
        viewModelScope.launch {
            LocalRepository.observe().combine(RemoteRepository.observe()) { local, remote ->
                local + remote
            }.collect { merged ->
                val sorted = merged.sortedByDescending { it.timestamp }
                allSportResults = sorted
                applyFilter()
            }
        }
    }

    fun changeShowRemote() {
        state = state.copy(showRemote = !state.showRemote)
        applyFilter()
    }

    fun changeShowLocal() {
        state = state.copy(showLocal = !state.showLocal)
        applyFilter()
    }

    private fun applyFilter() {
        val showNone = !state.showLocal && !state.showRemote
        val showRemote = state.showRemote || showNone // Showing none is equivalent to showing all
        val showLocal = state.showLocal || showNone // Showing none is equivalent to showing all
        val filtered = allSportResults.filter { (it.remote && showRemote) || (!it.remote && showLocal) }
        state = state.copy(sportResults = filtered)
    }
}