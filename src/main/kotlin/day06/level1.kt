package day06

import Level




class Day06lvl1() : Level("/day06/input.txt"){

    var result = 1;

    override fun onLines(lines: List<String>) {
        val lines2 = lines.map { it.replace(Regex("\\s+"), " ") }

        val times = lines2.first().split(": ").last().split(" ").map { it.toInt() }
        val distances = lines2.last().split(": ").last().split(" ").map { it.toInt() }

        result = times.zip(distances).map { (time, dist)->
                var possibilites = 0
                for(buttonTime in 0..time){
                    val timeLeft = time - buttonTime;
                    if(buttonTime * timeLeft > dist) possibilites++;
                }
                return@map possibilites
        }.reduce{ acc, cur -> acc*cur }

    }

    override fun onResult(): String = "${this.result}"
}

fun main(){
    Day06lvl1().start()
}