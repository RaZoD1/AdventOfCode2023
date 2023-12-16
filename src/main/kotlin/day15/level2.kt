package day15

import Level


class Day15lvl2 : Level("/day15/input.txt") {

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
        val strings = text.split(",")

        val boxes: Array<MutableList<Pair<String, Int>>> = Array(265) { mutableListOf() }

        for (string in strings) {
            val label = string.split(Regex("[-=]")).first()
            val hash = hash(label)
            val operation = string.split(Regex("[-=]")).last()

            if (string.contains("-")) {
                val box = boxes[hash]
                val index = box.indexOfFirst { (lensLabel, focus) -> label == lensLabel}
                if (index != -1) box.removeAt(index)
            } else {
                val focal = operation[0].digitToInt()

                if(boxes[hash].none { (lensLabel, focus) -> label == lensLabel }){
                    boxes[hash].add(label to focal)
                } else {
                    boxes[hash]
                        .indexOfFirst { (lensLabel, focus) -> label == lensLabel }
                        .let { index -> boxes[hash][index] = label to focal }
                }
            }
        }

        result = boxes.mapIndexed { index, box -> (index + 1) to box }
            .filter { (nr, box) -> box.isNotEmpty() }
            .sumOf { (boxNr, box) ->
                 box.mapIndexed { slot, lens -> (boxNr * (slot+1) * lens.second).also { println("Box $boxNr Slot $slot Label ${lens.first} Focal ${lens.second}") } }.sum()
            }.toLong()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    Day15lvl2().start()
}