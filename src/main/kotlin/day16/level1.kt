package day16

import Level
import Vec2
import day10.Direction
import java.util.LinkedList
import java.util.Queue
import kotlin.system.measureTimeMillis

enum class BeamPiece(val char: Char, val paths: Map<Direction, List<Direction>>) {
    EMPTY('.', Direction.entries.associateWith { listOf(it) }),
    MIRROR1('/', mapOf(
        Direction.NORTH to listOf(Direction.EAST),
        Direction.EAST to listOf(Direction.NORTH),
        Direction.SOUTH to listOf(Direction.WEST),
        Direction.WEST to listOf(Direction.SOUTH)
    )),
    MIRROR2('\\', mapOf(
        Direction.NORTH to listOf(Direction.WEST),
        Direction.EAST to listOf(Direction.SOUTH),
        Direction.SOUTH to listOf(Direction.EAST),
        Direction.WEST to listOf(Direction.NORTH)
    )),
    SPLITTER_H('-', mapOf(
        Direction.NORTH to listOf(Direction.WEST, Direction.EAST),
        Direction.EAST to listOf(Direction.EAST),
        Direction.SOUTH to listOf(Direction.WEST, Direction.EAST),
        Direction.WEST to listOf(Direction.WEST)
    )),
    SPLITTER_V('|', mapOf(
        Direction.NORTH to listOf(Direction.NORTH),
        Direction.EAST to listOf(Direction.NORTH, Direction.SOUTH),
        Direction.SOUTH to listOf(Direction.SOUTH),
        Direction.WEST to listOf(Direction.NORTH, Direction.SOUTH)
    )),
}

data class BeamMap(val grid: List<List<BeamPiece>>){
    companion object{
        fun parse(lines: List<String>): BeamMap = BeamMap(
            lines.map { line ->
                line.toCharArray().map { char -> BeamPiece.entries.first { it.char == char } }
            }
        )
    }

    fun at(pos: Vec2): BeamPiece = this.grid[pos.row][pos.col]

    fun energizedPieces(startPos: Vec2 = Vec2(0,0), startDir: Direction = Direction.EAST ): Map<Vec2, Int> {

        val start: Pair<Vec2, Direction> = startPos to startDir
        val visited = mutableSetOf<Pair<Vec2, Direction>>()
        val queue: Queue<Pair<Vec2, Direction>> = LinkedList()

        queue.add(start)
        while (queue.isNotEmpty()) {
            val (pos, dir) = queue.poll()
            if (visited.contains(pos to dir)) continue
            visited.add(pos to dir)
            val piece = at(pos)
            val paths = piece.paths[dir] ?: emptyList()
            for (path in paths) {
                val newPos = pos + path.vec2
                if (inBounds(newPos)) {
                    queue.add(newPos to path)
                }
            }
        }

        val visitations = visited.groupBy { it.first }.map { it.key to it.value.size }.toMap()
        return visitations
    }

    fun printEnergized() {
        val energized = energizedPieces()
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val pos = Vec2(col, row)
                if (energized.containsKey(pos)) {
                    print('#')
                } else {
                    print('.')
                }
            }
            println()
        }
    }

    fun countEnergized(): Int {
        return energizedPieces().size
    }


    private fun inBounds(pos: Vec2): Boolean {
        return pos.row in grid.indices && pos.col in grid[pos.row].indices
    }
}

class Day16lvl1 : Level("/day16/input.txt") {

    var result = 0L;


    override fun onLines(lines: List<String>) {
        val beamMap = BeamMap.parse(lines)

        //beamMap.printEnergized()
        val energizedCells = beamMap.countEnergized()
        result = energizedCells.toLong()
    }

    override fun onResult(): String = "${this.result}"
}

fun main() {

    val time = measureTimeMillis { Day16lvl1().start() }
    println("Time: ($time milliseconds)")
}