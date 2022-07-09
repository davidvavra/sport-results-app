package me.vavra.sportresults.model

data class NewSportResultState(
    val sportResult: SportResult,
    val nameValid: Boolean,
    val placeValid: Boolean,
    val durationValid: Boolean,
    val savingState: SavingState?,
)

enum class SavingState { IN_PROGRESS, ERROR, SUCCESS }
