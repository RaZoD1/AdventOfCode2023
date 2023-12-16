package day15

import Level


class Day15lvl1 : Level("/day15/input.txt") {

    var result = 0L;

    fun hash(input: String): Int {
        var current = 0;

        for(c in input){
            current += c.code
            current *= 17
            current %= 256
        }
        return current
    }

    override fun onText(text: String) {
        result = text.split(",").map { hash(it) }.sum().toLong()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day15lvl1().start()
}