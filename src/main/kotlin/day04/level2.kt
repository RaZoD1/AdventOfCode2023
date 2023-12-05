package day04

import Level

class Day04lvl2() : Level("/day04/input.txt"){

    private var sum  = 0;

    override fun onLines(lines: List<String>) {
        val cards = lines.map { ScratchCard.parse(it) }
        val cardAmountMap = cards.associateBy({it.id}, {1}).toMutableMap()

        for (card in cards) {
            val wins = card.wins()
            val amount = cardAmountMap[card.id]!!;

            if(wins > 0) {
                for (i in (card.id + 1)..card.id + wins) {
                    cardAmountMap[i] = (cardAmountMap[i]?:0) + amount
                }
            }
        }

        sum = cardAmountMap.values.sum()
    }

    override fun onResult(): String = "${this.sum}"
}

fun main(){
    Day04lvl2().start()
}