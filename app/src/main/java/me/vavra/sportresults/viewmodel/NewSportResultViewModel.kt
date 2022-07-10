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
            nameValid = true,
            placeValid = true,
            durationValid = true,
            savingState = null
        )
    )
        private set

    fun nameChanged(name: String) {
        state = state.copy(
            sportResult = state.sportResult.copy(name = name)
        )
        validateName()
    }

    fun placeChanged(place: String) {
        state = state.copy(
            sportResult = state.sportResult.copy(place = place),
        )
        validatePlace()
    }

    fun durationChanged(duration: String) {
        val newDuration = duration.toIntOrNull() ?: EMPTY_DURATION
        state = state.copy(
            sportResult = state.sportResult.copy(duration = newDuration)
        )
        validateDuration()
    }

    fun remoteChanged(remote: Boolean) {
        state = state.copy(sportResult = state.sportResult.copy(remote = remote))
    }

    fun save() {
        // Run validations before saving, because everything is valid at the beginning
        // (showing validation errors at he beginning is a bad UX)
        validateName()
        validatePlace()
        validateDuration()
        // Only proceed if everything is valid
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

    private fun validateName() {
        val isValid = state.sportResult.name.isNotBlank()
        state = state.copy(
            nameValid = isValid
        )
    }

    private fun validatePlace() {
        val isValid = state.sportResult.place.isNotBlank()
        state = state.copy(
            placeValid = isValid
        )
    }

    private fun validateDuration() {
        val isValid = state.sportResult.duration > 0
        state = state.copy(
            durationValid = isValid
        )
    }
}