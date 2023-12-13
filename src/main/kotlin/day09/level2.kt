package day09

import Level


class Day09lvl2() : Level("/day09/input.txt") {

    var result = 0;

    fun extrapolateValueBack(values: List<Int>): Int{

        val diffs = mutableListOf<Int>()
        for(i in 1..<values.size)
        {
            diffs.add(values[i] - values[i-1])
        }
        return if(diffs.all { it == diffs.first() }) values.first() - diffs.first() else values.first() - extrapolateValueBack(diffs)
    }

    override fun onLine(line: String) {
        result += extrapolateValueBack(line.split(" ").map(String::toInt)).also(::println)
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day09lvl2().start()
}