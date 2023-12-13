package day13

import Level
import kotlin.math.min

data class Mirrors2(val grid: List<String>){
    companion object {
        fun parse(text: String): Mirrors2 {
            val grid = text.split("\n")
            return Mirrors2(grid)
        }
    }

    fun reflectionStuff(): Int {
        var rowCount = rowReflection(grid)
        var colCount = rowReflection(transpose(grid))

        println("ORow: $rowCount, Col: $colCount")

        rowCount = rowReflection(grid, rowCount)
        colCount = rowReflection(transpose(grid), colCount)

        println("Row: $rowCount, Col: $colCount")

        return rowCount * 100 + colCount
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

    fun matchWithSmudge(s1: String, s2: String): Pair<Boolean, Boolean> {

        var smudgeFixed = false
        for(i in s1.indices){
            if(s1[i] != s2[i]){
                if(smudgeFixed) return false to smudgeFixed
                else smudgeFixed = true
            }
        }
        return true to smudgeFixed
    }
    fun rowReflection(grid: List<String>, original: Int? = null): Int {
        val rows = grid.size
        val checkSmudge = original != null

        outer@for(i in 0..<(rows - 1)){

            if(i + 1 == original) continue@outer

            var smudgeFixed = false
            for(j in 0..(min(i, rows - i - 2))){
                if(checkSmudge){
                    if(smudgeFixed){
                        if(grid[i-j] != grid[i + j + 1]) continue@outer
                    } else {
                        val (matches, fixed) = matchWithSmudge(grid[i-j], grid[i + j + 1])
                        if(!matches) continue@outer
                        if(fixed) smudgeFixed = true
                    }
                } else if(grid[i-j] != grid[i + j + 1]) continue@outer
            }
            return i + 1
        }
        return 0;
    }

}

class Day13lvl2() : Level("/day13/input.txt") {

    var result = 0L;


    override fun onText(text: String) {

        text
            .split("\n\n")
            .map { Mirrors2.parse(it) }
            .sumOf { it.reflectionStuff() }
            .let { result += it }
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    Day13lvl2().start()
}