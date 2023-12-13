package day12

import Level

private data class SpringRecord(val springs: String, val arrangement: List<Int>){
    companion object{
        fun parse(line: String): SpringRecord {
            val arrangements = line.split(" ").last().split(",").map { it.toInt() }
            val springs = line.split(" ").first()
            return SpringRecord(springs, arrangements)
        }
    }

    fun matchesArrangement(possibleLine: String): Boolean{

        var line = possibleLine;
        for (count in arrangement){
            line = line.trimStart { it == '.' }
            if(line.substringBefore(".").length != count) return false
            line = line.trimStart {it == '#'}
        }

        return line.all { it == '.' }
    }

    fun findVariations(): Int {
        return recFindVariations(this.springs, 0).size

    }

    fun recFindVariations(string: String, depth: Int): Set<String> {
        //println("Depth: $depth")
        val posToTry = string.indexOfFirst { it == '?' }

        if(posToTry == -1) return if(matchesArrangement(string)) setOf(string) else emptySet()

        val var1 = string.replaceRange(posToTry, posToTry+1, "#")
        val var2 = string.replaceRange(posToTry, posToTry+1, ".")

        return recFindVariations(var1, depth +1) + recFindVariations(var2, depth +1)
    }
}
class Day12lvl1() : Level("/day12/input.txt") {

    var result = 0L;


    override fun onLine(line: String) {
        val record = SpringRecord.parse(line)
        val vars = record.findVariations();
        result += vars
        println("Finished: $vars variations")
    }


    override fun onResult(): String = "${this.result}"
}

fun main() {

   /* val record = SpringRecord.parse("???.### 1,1,3")

    println(record.matchesArrangement("#.#.###"))
    println(record.matchesArrangement("##..###"))*/
    Day12lvl1().start()
}