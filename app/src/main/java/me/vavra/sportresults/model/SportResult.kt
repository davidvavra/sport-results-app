package me.vavra.sportresults.model

data class SportResult(val name: String, val place: String, val duration: Int, val remote: Boolean) {

    companion object {
        const val EMPTY_DURATION = Int.MIN_VALUE
    }
}
