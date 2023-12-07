package day07

import Level
import kotlin.math.min


private val cardRanking = listOf(
    'A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J'
)

private fun Char.cardRank() = cardRanking.indexOf(this)

data class PokerBet2(val hand: String, val bid: Int) {
    fun highestHand(): Hands {
        val characterMap = hand.toCharArray().groupBy { it }.filterNot { it.key == 'J' }
        val jokerCount = hand.count { it == 'J' }

        if (characterMap.count() == 1 || jokerCount == 5) return Hands.FIVE_OF_A_KIND
        if (characterMap.count() == 2 && characterMap.any { (it.value.size + jokerCount)  == 4 }) return Hands.FOUR_OF_A_KIND
        if (characterMap.count() <= 2) return Hands.FULL_HOUSE
        if (characterMap.any { (it.value.size + jokerCount) == 3 }) return Hands.THREE_OF_A_KIND


        if (jokerCount == 0 && characterMap.count { it.value.size == 2 } == 2) return Hands.TWO_PAIR

        if (characterMap.any { (it.value.size + min(jokerCount, 1)) == 2 }) return Hands.ONE_PAIR
        return Hands.HIGH_CARD
    }


};
class Day07lvl2() : Level("/day07/input.txt") {

    var result = 0;

    override fun onLines(lines: List<String>) {

        result = lines
            .map { PokerBet2(it.split(" ").first(), it.split(" ").last().toInt()) }
            .sortedWith(compareBy({ it.highestHand() }, { it.hand[0].cardRank() }, { it.hand[1].cardRank() }, { it.hand[2].cardRank() }, { it.hand[3].cardRank() }, { it.hand[4].cardRank() }))
            .onEach { if(it.hand.contains("J")) println("$it ${it.highestHand()}") }
            .reversed()
            .mapIndexed { index, pokerBet -> (index+1)*pokerBet.bid }
            .sum()

    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day07lvl2().start()
}
