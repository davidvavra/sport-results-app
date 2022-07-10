package me.vavra.sportresults.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.vavra.sportresults.model.NewSportResultState
import me.vavra.sportresults.model.SavingState
import me.vavra.sportresults.model.SportResult
import me.vavra.sportresults.model.SportResult.Companion.EMPTY_DURATION
import me.vavra.sportresults.storage.LocalStorageRepository

class NewSportResultViewModel : ViewModel() {
    var state by mutableStateOf(
        NewSportResultState(
            sportResult = SportResult(
                name = "",
                place = "",
                durationMinutes = EMPTY_DURATION,
                remote = true,
                timestamp = 0
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
            sportResult = state.sportResult.copy(durationMinutes = newDuration)
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
                try {
                    if (state.sportResult.remote) {

                    } else {
                        LocalStorageRepository.new(state.sportResult)
                    }
                    state = state.copy(savingState = SavingState.SUCCESS)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    state = state.copy(savingState = SavingState.ERROR)
                }
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
        val isValid = state.sportResult.durationMinutes > 0
        state = state.copy(
            durationValid = isValid
        )
    }
}