package day03

import Vec2
import getResourceAsText

fun main(){
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

        if(c == '*'){ // is maybe gear
            print("\nFound maybe gear on $col $row")
            val positionsToCheck = getPositionsToCheck(Vec2(col, row), cols, rows).toMutableList()

            var numbers = mutableListOf<Int>()

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

                numbers.add(number)
            }

            if(numbers.count() == 2){
                val power = numbers.first() * numbers.last()
                print(" - Gear confirmed power $power")
                sum += power
            }
        }
    }


    println("\n$sum")
}