package day14

import Level



val CYCLES = 1_000_000_000L
fun RockPlatform.cycle(): RockPlatform {
    return this.rollNorth().rollWest().rollSouth().rollEast()
}

data class CycleResult(val length: Long, val offset: Long, val platform: RockPlatform)
fun RockPlatform.checkCycles():CycleResult{
    var current = this
    var count = 0L
    val previous = mutableMapOf<RockPlatform, Long>()
    while(count < CYCLES){
        previous[current] = count
        current = current.cycle()
        count++
        if(previous.contains(current)){
            break;
        }
    }
    return CycleResult(count-previous[current]!!, previous[current]!!, current)
}

fun RockPlatform.calculateCycleLoad(): Long {

    var (cycleLen, offset, platform) = this.checkCycles().also { println("Cycle: $it") }

    val remainingCycles = (CYCLES - offset) % cycleLen
    println("Remaining cycles: $remainingCycles")
    for(i in 0..<remainingCycles){
        platform = platform.cycle()
    }


    return platform.calculateLoad()
}
class Day14lvl2() : Level("/day14/input.txt") {

    var result = 0L;


    override fun onLines(lines: List<String>) {
        val platform = RockPlatform(lines)

        result = platform.calculateCycleLoad()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    Day14lvl2().start()
}