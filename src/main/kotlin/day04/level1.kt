package day04

import getResourceAsText

data class ScratchCard(val id: Int, val winningNumbers: HashSet<Int>, val numbers: List<Int>){
    companion object {
        fun parse(line: String): ScratchCard {
            // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            val (card, numbersRaw) = line.split(":").map { it.trim().replace("\\s+".toRegex(), " ") }
            val cardId = card.split(" ").last().toInt()

            val winningNumbers = numbersRaw.split("|").first().trim().split(" ").map { it.toInt() }.toHashSet()
            val numbers = numbersRaw.split("|").last().trim().split(" ").map { it.toInt() }

           return ScratchCard(cardId, winningNumbers, numbers)
        }
    }

    fun score(): Int {
        var score = 0;

        for (number in numbers) {
            if(winningNumbers.contains(number)){
                score = if(score == 0) 1 else score * 2
            }

        }
        return score
    }

    fun wins(): Int  = numbers.count { winningNumbers.contains(it) }
}



fun main(){
    println("Hey")
    val rawText = getResourceAsText("/day04/input.txt")?.trim()?.replace("\r", "")
    if(rawText == null) {
        println("Resource Null")
        return
    }

    val lines = rawText.split("\n").filterNot { it.isEmpty()}

    val sum = lines.sumOf { ScratchCard.parse(it).score() }

    println("$sum")
}