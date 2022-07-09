package me.vavra.sportresults.model

data class SportResultsState(
    val sportResults: List<SportResult>,
    val showRemote: Boolean,
    val showLocal: Boolean
)
