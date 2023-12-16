package day10


import Level
import Vec2

enum class Direction(val vec2: Vec2){
    NORTH(Vec2.UP), SOUTH(Vec2.DOWN), WEST(Vec2.LEFT), EAST(Vec2.RIGHT);
    fun opposite(): Direction = when(this){
        NORTH->SOUTH
        SOUTH->NORTH
        WEST->EAST
        EAST->WEST
    }
}

enum class PipePiece(val char: Char, val directions: List<Direction>){
    VERTICAL('|', listOf(Direction.NORTH, Direction.SOUTH)),
    HORIZONTAL('-', listOf(Direction.EAST, Direction.WEST)),
    NORTH_EAST('L', listOf(Direction.NORTH, Direction.EAST)),
    NORTH_WEST('J', listOf(Direction.NORTH, Direction.WEST)),
    SOUTH_WEST('7', listOf(Direction.SOUTH, Direction.WEST)),
    SOUTH_EAST('F', listOf(Direction.SOUTH, Direction.EAST)),
    GROUND('.', emptyList()),
    START('S', Direction.entries);

    override fun toString() = "$char"
}


class PipeMap(val grid: List<List<PipePiece>>){
    companion object{
        fun parse(lines: List<String>): PipeMap = PipeMap(
            lines.map { line ->
                line.toCharArray().map { char -> PipePiece.entries.first { it.char == char } }
            }
        )
    }

    val rows = grid.size
    val cols = grid.first().size

    fun getStartPos(): Vec2 {
        for (rowIdx in grid.indices) {
            val row = grid[rowIdx]
            for (colIdx in row.indices) {
                val element = row[colIdx]
                if(element == PipePiece.START) return Vec2(col = colIdx, row = rowIdx)
            }
        }
        throw Exception("No start found")
    }

     fun at(pos: Vec2): PipePiece = this.grid[pos.row][pos.col]

    private fun inBounds(pos: Vec2): Boolean = pos.row in 0..<rows && pos.col in 0..<cols
    fun neighborPieces(pos: Vec2): List<Vec2> {
        val piece = at(pos)
        val directions = piece.directions

        return directions.map { it to (it.vec2+pos) }
            .filter { inBounds(it.second) }
            .filter { it.first.opposite() in at(it.second).directions }
            .map {it.second}
    }

    private var _loopPieces: Map<Vec2, PipePiece>? = null
    fun getLoopPieces(): Map<Vec2, PipePiece> {
        if(_loopPieces != null) return _loopPieces!!
        val start = getStartPos()
        val checked = mutableSetOf<Vec2>()
        val toCheck = mutableListOf<Vec2>()
        var neighbors = neighborPieces(start)
        checked.add(start)
        toCheck.add(neighbors.first())
        while(toCheck.isNotEmpty()){
            val checking = toCheck.removeFirst()
            checked.add(checking)
            toCheck.addAll(neighborPieces(checking).filterNot { checked.contains(it) })
        }
        return checked.associateWith { at(it) }.also { _loopPieces = it }
    }

    fun inLoop(pos: Vec2): Boolean = getLoopPieces().contains(pos)

    fun loopLength(): Int {
        val start = getStartPos()
        val checked = mutableSetOf<Vec2>()
        val toCheck = mutableListOf<Vec2>()
        val neighbors = neighborPieces(start)
        var count = 0;
        checked.add(start)
        toCheck.add(neighbors.first())
        while(toCheck.isNotEmpty()){
            val checking = toCheck.removeFirst()
            checked.add(checking)
            toCheck.addAll(neighborPieces(checking).filterNot { checked.contains(it) })
            count ++;
        }
        return count;
    }


}

class Day10lvl1() : Level("/day10/input.txt") {

    var result = 0;

    override fun onLines(lines: List<String>) {
        val map = PipeMap.parse(lines)

        val start = map.getStartPos().also(::println)

        //map.neighborPieces(Vec2(0, 3)).also { println(it) }
        val loopLength = map.loopLength().also { println("Length: $it") }

        result = loopLength / 2 + 1;
    }


    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day10lvl1().start()
}















