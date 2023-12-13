package day12

import Level

private data class SpringRecord2(val springs: String, val arrangement: List<Int>) {
    companion object {
        fun parse(line: String): SpringRecord2 {
            val arrangements = line.split(" ").last().split(",").map { it.toInt() }
            val springs = line.split(" ").first()
            return SpringRecord2(springs, arrangements)
        }
    }


    var cached: MutableMap<String, Long> = mutableMapOf()
    fun findVariations(): Long {
        cached = mutableMapOf()
        return recFindVariations(this.springs, this.arrangement)
    }


    private fun recFindVariations(pattern: String, groups: List<Int>): Long {

        val key = "$pattern ${groups.joinToString(",")}"
        if(cached.containsKey(key)) return cached[key]!!

        val space = pattern.length

        if(groups.size == 1){
            val result = (0..(space - groups[0])).map {
                ".".repeat(it) +
                        "#".repeat(groups[0]) +
                        ".".repeat(space - groups[0] - it)
            }.count {possibleLine ->
                isMatching(possibleLine, pattern)
            }
            cached[key] = result.toLong()
            return result.toLong()
        }

        val (first, rest) = groups.first() to groups.drop(1)
        val restMinSpace = rest.sum() + rest.size - 1
        val firstPossiblePositions = space - restMinSpace - first

        val result: Long = (0..firstPossiblePositions).map { firstPos ->
            val curStr = ".".repeat(firstPos) + "#".repeat(first) + "."
            if(!isMatching(curStr, pattern.substring(0, curStr.length))) return@map 0

            recFindVariations(pattern.substring(curStr.length), rest)
        }.sum()

        cached[key] = result
        return result
    }

    fun isMatching(str: String, pattern: String): Boolean {
        if(str.length != pattern.length) throw IllegalArgumentException("Lengths don't match")
        for (i in str.indices) {
            if (pattern[i] == '?') continue
            if (pattern[i] != str[i]) return false
        }
        return true
    }


}

class Day12lvl2() : Level("/day12/input.txt") {

    var result = 0L;


    override fun onLine(line: String) {
        var record = SpringRecord2.parse(line)
        val newLine = List(5) { record.springs }.joinToString("?")
        val newArrangement = List(5) { line.split(" ").last() }.joinToString(",").split(",").map { it.toInt() }
        record = SpringRecord2(newLine, newArrangement)
        //println(record.arrangement.joinToString(", "))
        //println("Qs: ${record.springs.count { it == '?' }}")
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
    Day12lvl2().start()
    /*val record = SpringRecord2.parse("?#.??????#??#?#?#?#? 1,1,15")
    println(record.mayMatch(".#.##?##############"))*/
}