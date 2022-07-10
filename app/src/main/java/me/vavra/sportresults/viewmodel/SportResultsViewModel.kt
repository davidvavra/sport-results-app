package me.vavra.sportresults.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.vavra.sportresults.model.SportResultsState
import me.vavra.sportresults.storage.LocalStorageRepository

class SportResultsViewModel : ViewModel() {
    var state by mutableStateOf(
        SportResultsState(
            sportResults = listOf(),
            showRemote = true,
            showLocal = true
        )
    )
        private set

    init {
        viewModelScope.launch {
            LocalStorageRepository.observe().collect { local ->
                val all = local.sortedByDescending { it.timestamp }
                state = state.copy(sportResults = all)
            }
        }
    }

    fun changeShowRemote() {
        state = state.copy(showRemote = !state.showRemote)
    }

    fun changeShowLocal() {
        state = state.copy(showLocal = !state.showLocal)
    }
}