package day06

import Level


class Day06lvl2() : Level("/day06/input.txt"){

    var result: Long = 1;

    override fun onLines(lines: List<String>) {
        val lines2 = lines.map { it.replace(Regex("\\s+"), "") }

        val time = lines2.first().split(":").last().toLong()
        val distance = lines2.last().split(":").last().toLong()

        var possibilites:Long = 0
        for(buttonTime in 0..time){
            val timeLeft = time - buttonTime;
            if(buttonTime * timeLeft > distance) possibilites++;
        }

        result = possibilites
    }

    override fun onResult(): String = "${this.result}"
}

fun main(){
    Day06lvl2().start()
}