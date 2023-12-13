package day03

import Vec2
import getResourceAsText

fun getPositionsToCheck(pos: Vec2, cols: Int, rows: Int): List<Vec2> {
    val positions = mutableListOf<Vec2>()

    if(pos.col > 0) positions.add(pos+ Vec2.LEFT)
    if(pos.col < cols - 1) positions.add(pos+ Vec2.RIGHT)
    if(pos.row > 0) positions.add(pos+ Vec2.UP)
    if(pos.row < rows - 1) positions.add(pos+ Vec2.DOWN)
    if(pos.col > 0 && pos.row > 0) positions.add(pos+ Vec2.UP_LEFT)
    if(pos.col < cols - 1 && pos.row > 0) positions.add(pos+ Vec2.UP_RIGHT)
    if(pos.col > 0 && pos.row < rows - 1) positions.add(pos+ Vec2.DOWN_LEFT)
    if(pos.col < cols - 1 && pos.row < rows - 1) positions.add(pos+ Vec2.DOWN_RIGHT)

    return positions
}

fun main(){
    println("Hey")
    val rawText = getResourceAsText("/day03/hofer.txt")?.trim()?.replace("\r", "")
    if(rawText == null) {
        println("Resource Null")
        return
    }
    println(rawText)
    val text = rawText.filterNot { it == '\n' || it == ' ' }
    val lines = rawText.split("\n").filter{it.isNotEmpty()}

    val rows = lines.count()
    val cols = lines.first().length

    println("Input has $cols cols X $rows rows")

    var sum = 0;

    for (i in text.indices) {
        val c = text[i];
        val col = i % cols
        val row = i / cols

        if(!(c.isDigit() || c == '.')){
            print("\nFound symbol on $col $row $c")
            val positionsToCheck = getPositionsToCheck(Vec2(col, row), cols, rows).toMutableList()

            while(positionsToCheck.isNotEmpty()){
                val (col, row) = positionsToCheck.removeFirst()
                if(!lines[row][col].isDigit()) continue;
                var start = col;var end = col;
                while(start>0) {
                    if (lines[row][start - 1].isDigit()) {
                        start--
                        positionsToCheck.remove(Vec2(start, row))
                    } else break;
                }
                while(end < cols - 1) {
                    if (lines[row][end + 1].isDigit()) {
                        end++
                        positionsToCheck.remove(Vec2(end, row))
                    }
                    else break;
                }
                val number = lines[row].substring(start, end+1).toInt()
                print(" - num $number")

                sum+=number
            }
        }
    }


    println("\n$sum")
}