package me.vavra.sportresults.model

data class SportResult(
    val name: String,
    val place: String,
    val durationMinutes: Int,
    val remote: Boolean,
    val timestamp: Long
) {

    companion object {
        const val EMPTY_DURATION = Int.MIN_VALUE
    }
}
