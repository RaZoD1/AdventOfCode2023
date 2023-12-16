package day14

import Level

class RockPlatform(val grid: List<String>){

    private fun rollRight(): RockPlatform {
        val rows = mutableListOf<String>();
        for(initRow in grid){
            val space = initRow.length

            var newRow = "";
            var current: Int = 0;

            var row = initRow
            while(current < space){
                val nextStopper = row.indexOfFirst { it == '#'  }.takeIf { it >= 0 } ?: space
                val stones = row.substring(current, nextStopper).count { it == 'O' }
                if(nextStopper == space){
                    newRow += ".".repeat(space-current-stones) + "O".repeat(stones)
                    break;
                }
                newRow += ".".repeat(nextStopper-current-stones) + "O".repeat(stones) + "#"
                row = row.replaceRange(current, nextStopper+1, ".".repeat((nextStopper+1)-current))
                current = nextStopper+1
            }
            if(newRow.length != row.length){
                println("${newRow.length}, ${row.length} : $newRow, $row")
                throw Exception("Row length changed")
            }
            rows.add(newRow)
        }
        return RockPlatform(rows)
    }

    fun turnCounterClockwise(): RockPlatform{
        val rows = mutableListOf<String>();
        for(col in 0..<grid[0].length){
            var newRow = "";
            for(row in grid){
                newRow += row[grid[0].length - col - 1]
            }
            rows.add(newRow)
        }
        return RockPlatform(rows)
    }

    fun turnClockwise(): RockPlatform {
        return turnCounterClockwise().turnCounterClockwise().turnCounterClockwise()
    }

    fun rollNorth(): RockPlatform {
        return turnClockwise().rollRight().turnCounterClockwise()
    }
    fun rollSouth(): RockPlatform {
        return turnCounterClockwise().rollRight().turnClockwise()
    }

    fun rollEast(): RockPlatform {
        return rollRight()
    }
    fun rollWest(): RockPlatform {
        return turnCounterClockwise().turnCounterClockwise().rollRight().turnCounterClockwise().turnCounterClockwise()
    }

    fun calculateLoad(): Long {
        return grid.mapIndexed{ row, line ->
            (line.count{ it == 'O' } * (grid.size - row).toLong()).also { println("$row: $it") }
        }.sum()
    }

    override fun toString(): String {
        return grid.joinToString("\n")
    }

    override fun equals(other: Any?): Boolean {
        if(other !is RockPlatform){
            return false
        }
        return this.grid.zip(other.grid).all { it.first == it.second }
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }
}

class Day14lvl1() : Level("/day14/input.txt") {

    var result = 0L;


    override fun onLines(lines: List<String>) {
        val platform = RockPlatform(lines)

        /*println(platform.grid.joinToString("\n"))
        println()
        println(platform.turnClockwise().grid.joinToString("\n"))
*/

        result = platform.rollNorth().also{
            it.grid.joinToString("\n").also { println(it) }
        }.calculateLoad()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    Day14lvl1().start()
}