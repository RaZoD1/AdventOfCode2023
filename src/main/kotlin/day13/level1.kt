package day13

import Level
import kotlin.math.min

data class Mirrors(val grid: List<String>){
    companion object {
        fun parse(text: String): Mirrors {
            val grid = text.split("\n")
            return Mirrors(grid)
        }
    }

    fun reflectionStuff(): Long {
        val rowCount: Long = (rowReflection(grid) * 100).toLong()
        val colCount: Long = rowReflection(transpose(grid)).toLong()

        println("Row: $rowCount, Col: $colCount")

        return rowCount + colCount
    }


    private fun transpose(grid: List<String>): List<String> {
        val rows = grid.size
        val cols = grid[0].length

        val result = mutableListOf<String>()
        for (col in 0 until cols) {
            var rowStr = ""
            for (row in 0 until rows) {
                rowStr = "$rowStr${grid[row][col]}"
            }
            result.add(rowStr)
        }
        return result
    }
    fun rowReflection(grid: List<String>): Int {
        val rows = grid.size

        outer@for(i in 0..<(rows - 1)){

            for(j in 0..(min(i, rows - i - 2))){
                if(grid[i-j] != grid[i + j + 1]) continue@outer
            }
            return i + 1
        }
        return 0;
    }

}

class Day13lvl1() : Level("/day13/input.txt") {

    var result = 0L;


    override fun onText(text: String) {

        text
            .split("\n\n")
            .map { Mirrors.parse(it) }
            .sumOf { it.reflectionStuff() }
            .let { result += it }
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    Day13lvl1().start()
}