package day08

import Level


data class Directions(val left: String, val right: String);
class Day08lvl1() : Level("/day08/input.txt") {

    var result = 0;
    val targetNode = "ZZZ"
    fun countStepsToEnd(instructions: List<Char>, map: Map<String, Directions>): Int{

        var count = 0;
        var currentNode = "AAA"
        while (currentNode != targetNode){
            val instruction = instructions[count % instructions.size]
            currentNode = when(instruction){
                'L' -> map[currentNode]!!.left
                'R' -> map[currentNode]!!.right
                else -> throw Exception("Instruction unclear")
            }
            count++;
        }
        return count;
    }

    override fun onLines(lines: List<String>) {
        val instructions = lines.first().toList()

        val map = lines
            .drop(1)
            .associate { line -> line.substring(0..2) to Directions(line.substring(7..9), line.substring(12..14)) }
            //.onEach(::println)

        result = countStepsToEnd(instructions, map)

    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day08lvl1().start()
}
