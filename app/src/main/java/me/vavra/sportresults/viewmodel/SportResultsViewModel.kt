package me.vavra.sportresults.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.model.SportResultsState

class SportResultsViewModel : ViewModel() {
    var state by mutableStateOf(
        SportResultsState(
            sportResults = listOf(
                SportResult("Tenis", "Praha", 45, true),
                SportResult("Plavání", "Praha", 60, false),
                SportResult("Squash", "Brno", 20, true)
            ),
            showRemote = true,
            showLocal = true
        )
    )
        private set

    fun changeShowRemote() {
        state = state.copy(showRemote = !state.showRemote)
    }

    fun changeShowLocal() {
        state = state.copy(showLocal = !state.showLocal)
    }
}