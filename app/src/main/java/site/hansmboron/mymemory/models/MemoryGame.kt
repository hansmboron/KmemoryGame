package site.hansmboron.mymemory.models

import site.hansmboron.mymemory.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {
    val cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    var numPairsFound = 0

    init {
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        val randomizedImages = (chosenImages + chosenImages).shuffled()
        cards = randomizedImages.map { MemoryCard(it) }
    }

    fun flipCard(position: Int): Boolean {
        val card = cards[position]
        var foundMatch = false

        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card flipped
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
        if (cards[position1].identifier != cards[position2].identifier) {
            return false
        }
        cards[position1].isMached = true
        cards[position2].isMached = true
        numPairsFound++
        return true
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMached) {
                card.isFaceUp = false
            }
        }
    }

    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceUp(position: Int): Boolean {
        return cards[position].isFaceUp
    }
}
