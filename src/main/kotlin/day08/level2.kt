package day08

import Level


class Day08lvl2() : Level("/day08/input.txt") {

    var result: Long = 0;

    private fun String.isTargetNode() = this.endsWith('Z')
    fun countStepsToEnd(startNode: String,instructions: List<Char>, map: Map<String, Directions>): Long {

        var count:Long = 0;
        var currentNode = startNode
        while (!currentNode.endsWith('Z')){
            val instruction = instructions[(count % instructions.size).toInt()]
            currentNode = when(instruction){
                'L' -> map[currentNode]!!.left
                'R' -> map[currentNode]!!.right
                else -> throw Exception("Instruction unclear")
            }
            count++;
        }
        return count;
    }

    fun findLCM(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }

            lcm += larger
        }
        return maxLcm
    }


    fun findLCMOfListOfNumbers(numbers: List<Long>): Long {
        var result: Long = numbers[0]
        for (i in 1 until numbers.size) {
            result = findLCM(result, numbers[i])
        }
        return result
    }
    fun countStepsForAllEnd(instructions: List<Char>, map: Map<String, Directions>): Long{
        val startNodes = map.keys.filter { it.endsWith('A') }.also(::println)
        var count = 0;

        val minSteps = startNodes.map { countStepsToEnd(it, instructions, map) }.also { println(it) }

        return findLCMOfListOfNumbers(minSteps)
    }

    override fun onLines(lines: List<String>) {
        val instructions = lines.first().toList()

        val map = lines
            .drop(1)
            .associate { line -> line.substring(0..2) to Directions(line.substring(7..9), line.substring(12..14)) }
        //.onEach(::println)

        result = countStepsForAllEnd(instructions, map)

    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day08lvl2().start()
}
