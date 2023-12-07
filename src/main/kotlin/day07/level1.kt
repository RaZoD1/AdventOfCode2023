package day07

import Level


private val cardRanking = listOf(
    'A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2'
)

private fun Char.cardRank() = cardRanking.indexOf(this)


enum class Hands(val value: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

data class PokerBet(val hand: String, val bid: Int) {
    fun highestHand(): Hands {
        val characterMap = hand.toCharArray().groupBy { it }

        if (characterMap.count() == 1) return Hands.FIVE_OF_A_KIND
        if (characterMap.count() == 2 && characterMap.any { it.value.size == 4 }) return Hands.FOUR_OF_A_KIND
        if (characterMap.count() == 2 && characterMap.any { it.value.size == 3 }) return Hands.FULL_HOUSE
        if (characterMap.any { it.value.size == 3 }) return Hands.THREE_OF_A_KIND
        if (characterMap.count { it.value.size == 2 } == 2) return Hands.TWO_PAIR
        if (characterMap.any { it.value.size == 2 }) return Hands.ONE_PAIR
        return Hands.HIGH_CARD
    }


};
class Day07lvl1() : Level("/day07/input.txt") {

    var result = 0;

    override fun onLines(lines: List<String>) {

        result = lines
            .map { PokerBet(it.split(" ").first(), it.split(" ").last().toInt()) }
            .sortedWith(compareBy({ it.highestHand() }, { it.hand[0].cardRank() }, { it.hand[1].cardRank() }, { it.hand[2].cardRank() }, { it.hand[3].cardRank() }, { it.hand[4].cardRank() }))
            .onEach { println("$it ${it.highestHand()}") }
            .reversed()
            .mapIndexed { index, pokerBet -> (index+1)*pokerBet.bid }
            .sum()

    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day07lvl1().start()
}
