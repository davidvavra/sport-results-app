package me.vavra.sportresults.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.vavra.sportresults.model.NewSportResultState
import me.vavra.sportresults.model.SavingState
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.model.SportResult.Companion.EMPTY_DURATION

class NewSportResultViewModel : ViewModel() {
    var state by mutableStateOf(
        NewSportResultState(
            sportResult = SportResult(
                name = "",
                place = "",
                duration = EMPTY_DURATION,
                remote = true
            ),
            nameValid = false,
            placeValid = false,
            durationValid = false,
            savingState = null
        )
    )
        private set

    fun nameChanged(name: String) {
        val valid = name.isNotBlank()
        state = state.copy(
            sportResult = state.sportResult.copy(name = name),
            nameValid = valid
        )
    }

    fun placeChanged(place: String) {
        val valid = place.isNotBlank()
        state = state.copy(
            sportResult = state.sportResult.copy(place = place),
            placeValid = valid
        )
    }

    fun durationChanged(duration: String) {
        val newDuration = duration.toIntOrNull() ?: EMPTY_DURATION
        val valid = newDuration != EMPTY_DURATION
        state = state.copy(
            sportResult = state.sportResult.copy(duration = newDuration),
            durationValid = valid
        )
    }

    fun remoteChanged(remote: Boolean) {
        state = state.copy(sportResult = state.sportResult.copy(remote = remote))
    }

    fun save() {
        if (state.nameValid && state.placeValid && state.durationValid) {
            viewModelScope.launch {
                state = state.copy(savingState = SavingState.IN_PROGRESS)
                delay(2000)
                state = state.copy(savingState = SavingState.ERROR)
                delay(2000)
                state = state.copy(savingState = SavingState.SUCCESS)
            }
        }
    }
}