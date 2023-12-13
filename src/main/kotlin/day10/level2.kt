package day10

import Level
import Vec2

fun PipePiece.matches(direction: Direction, other: PipePiece): Boolean {
    if (!this.directions.contains(direction)) return false;
    return other.directions.contains(direction.opposite())
}

class ExpandedMap(val map: PipeMap) {
    private fun PipeMap.toExpanded(): List<List<PipePiece?>> {
        val expMap = mutableListOf<MutableList<PipePiece?>>();
        val rows = this.grid
        var lastRow: MutableList<PipePiece?>? = null;
        for (rowIdx in rows.indices) {
            println("Working on row $rowIdx")
            val row = rows[rowIdx]
            val expRow: MutableList<PipePiece?> = mutableListOf();
            var lastElement: PipePiece? = null;
            for (colIdx in row.indices) {
                val element = row[colIdx]
                if (lastElement != null) {
                    expRow.add(
                        if (this.inLoop(Vec2(col = colIdx, row = rowIdx)) && lastElement.matches(Direction.EAST, element)) {
                            PipePiece.HORIZONTAL
                        } else null
                    )
                }
                expRow.add(element)
                lastElement = element;
            }

            if (lastRow != null) {
                val interRow = mutableListOf<PipePiece?>();
                lastElement = null;
                for (colIdx in row.indices) {
                    val lastRowEl = lastRow[colIdx * 2]
                    val nextEl = expRow[colIdx * 2]

                    if(lastElement != null) interRow.add(null)
                    interRow.add(
                        if (lastRowEl == null || nextEl == null) {
                            null
                        } else if (this.inLoop(Vec2(col = colIdx, row = rowIdx)) && lastRowEl.matches(Direction.SOUTH, nextEl)) {
                            PipePiece.VERTICAL
                        } else null
                    )
                    lastElement = nextEl;
                }
                expMap.add(interRow)
            }


            expMap.add(expRow);
            lastRow = expRow
        }

        return expMap;
    }
    private fun Vec2.toExpanded(): Vec2 = Vec2(this.col * 2, this.row * 2)

    val expandedGrid = map.toExpanded()
    val rowCount = expandedGrid.size
    val colCount = expandedGrid.first().size

    private fun inBounds(pos: Vec2): Boolean = pos.row in expandedGrid.indices && pos.col in expandedGrid[pos.row].indices
    fun at(pos: Vec2): PipePiece? =  this.expandedGrid[pos.row][pos.col]
    fun matchingPipes(pos: Vec2): Map<Vec2, PipePiece> {
        val piece = at(pos) ?: return mapOf();

        return piece.directions.map { it to (it.vec2 + pos) }
            .filter { inBounds(it.second) }
            .filter { if(at(it.second) == null) false else piece.matches(it.first, at(it.second)!!) }
            .associate { it.second to at(it.second)!! }
    }

    private var _loopPieces: Map<Vec2, PipePiece>? = null;
    fun getLoopPieces(): Map<Vec2, PipePiece> {
        if(_loopPieces != null) return _loopPieces!!
        val start = map.getStartPos().toExpanded()
        val checked = mutableSetOf<Vec2>()
        val toCheck = mutableListOf<Vec2>()
        var neighbors = matchingPipes(start)
        checked.add(start)
        toCheck.addAll(neighbors.keys)
        while(toCheck.isNotEmpty()){
            val checking = toCheck.removeFirst()
            checked.add(checking)
            toCheck.addAll(matchingPipes(checking).keys.filterNot { checked.contains(it) })
        }
        return checked.associateWith { at(it)!! }.also { _loopPieces = it }
    }

    fun inLoop(pos: Vec2): Boolean = getLoopPieces().keys.contains(pos)

    fun loopArea(): Int {
        val outside: HashSet<Vec2> = HashSet();
        val inside: HashSet<Vec2> = HashSet();

        val loopPositions = getLoopPieces().keys

        for(loopPos in loopPositions){
            val neighbors = Direction.entries
                .map { it.vec2 + loopPos }
                .filter { inBounds(it) }
                .filterNot { loopPositions.contains(it) || outside.contains(it) || inside.contains(it) }


            neighbors.forEach { neighbor ->
                if(neighbor in outside) return@forEach
                if(neighbor in inside) return@forEach
                val list: HashSet<Vec2> = HashSet()
                val toCheck: HashSet<Vec2> = HashSet<Vec2>().apply {
                    add(neighbor)
                }

                while(toCheck.isNotEmpty()){
                    val checking = toCheck.first().also { toCheck.remove(it) }
                    list.add(checking)
                    toCheck.addAll(Direction.entries
                        .map { it.vec2 + checking }
                        .filter { inBounds(it) }
                        .filterNot { loopPositions.contains(it) || toCheck.contains(it) || list.contains(it) })
                }

                if(list.any { it.row == 0 || it.row == (rowCount-1)
                            || it.col == 0 || it.col == (colCount -1) })
                {
                    outside.addAll(list)
                } else inside.addAll(list)

            }
        }

        return inside.count { at(it) != null };

    }

    override fun toString(): String = this.expandedGrid.joinToString("\n") { row -> row.map { it?.char ?: ' ' }.joinToString("") }

}

class Day10lvl2() : Level("/day10/input.txt") {

    var result = 0;

    override fun onLines(lines: List<String>) {
        val map = PipeMap.parse(lines)
        println("Parsed map")
        val expandedMap = ExpandedMap(map);
        println("Converted to expanded")

        println(expandedMap)

        result = expandedMap.loopArea()

    }


    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day10lvl2().start()
}