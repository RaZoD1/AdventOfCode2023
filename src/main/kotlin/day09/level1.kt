package day09

import Level


class Day09lvl1() : Level("/day09/input.txt") {

    var result = 0;

    fun extrapolateValue(values: List<Int>): Int{

        val diffs = mutableListOf<Int>()
        for(i in 1..<values.size)
        {
            diffs.add(values[i] - values[i-1])
        }
        return if(diffs.all { it == diffs.first() }) values.last() + diffs.first() else values.last() + extrapolateValue(diffs)
    }

    override fun onLine(line: String) {
        result += extrapolateValue(line.split(" ").map(String::toInt)).also(::println)
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day09lvl1().start()
}