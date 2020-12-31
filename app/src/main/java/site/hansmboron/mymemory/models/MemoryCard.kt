package site.hansmboron.mymemory.models

data class MemoryCard(
    val identifier: Int,
    var isFaceUp: Boolean = false,
    var isMached: Boolean = false
)