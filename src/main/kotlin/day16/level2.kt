package day16

import Level
import Vec2
import day10.Direction
import kotlin.system.measureTimeMillis

fun BeamMap.findMaxEnergized(): Int {

    val starts: Set<Pair<Vec2, Direction>> = grid.indices
        .flatMap { row -> listOf(Vec2(0, row) to Direction.EAST, Vec2(grid[row].size - 1, row) to Direction.WEST) }
        .union(
            grid[0].indices.flatMap { col -> listOf(Vec2(col, 0) to Direction.SOUTH, Vec2(col, grid.size-1) to Direction.NORTH) }
        )

    println("Starts: ${starts.size}")
    val max = starts.maxOf { energizedPieces(it.first, it.second).size }
    return max
}

class Day16lvl2 : Level("/day16/input.txt") {

    var result = 0L;


    override fun onLines(lines: List<String>) {
        val beamMap = BeamMap.parse(lines)

        //beamMap.printEnergized()
        val energizedCells = beamMap.findMaxEnergized()
        result = energizedCells.toLong()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    val time = measureTimeMillis { Day16lvl2().start() }
    println("Time: ($time milliseconds)")
}