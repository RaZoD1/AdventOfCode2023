package day11


import Level
import Vec2


class Day11lvl1() : Level("/day11/input.txt") {

    var result = 0;

    fun colDistance(map: Array<Array<Char>>, colIdx: Int) =
        if (map.none { it[colIdx] == '#' }) 2 else 1


    fun rowDistance(map: Array<Array<Char>>, rowIdx: Int) =
        if (!map[rowIdx].contains('#')) 2 else 1

    fun getGalaxies(map: Array<Array<Char>>): List<Vec2> {
        val galaxies = mutableListOf<Vec2>();
        map.forEachIndexed { rowIdx, row ->
            row.forEachIndexed { colIdx, c ->
                if (c == '#') galaxies.add(Vec2(colIdx, rowIdx))
            }
        }
        return galaxies
    }

    fun countDistances(map: Array<Array<Char>>): Int {
        val galaxies = getGalaxies(map)

        var distances = 0;

        println("Galaxies: ${galaxies.size}")
        for (i in galaxies.indices) {

            for (j in (i + 1)..<galaxies.size) {

                val (first, second) = (galaxies[i] to galaxies[j])


                var distance = 0;
                for (rowIdx in (
                        if (first.row < second.row) (first.row + 1)..second.row
                        else (second.row + 1)..first.row)
                ) {
                    distance += rowDistance(map, rowIdx)
                }
                for (colIdx in (
                        if (first.col < second.col) (first.col + 1)..second.col
                        else (second.col + 1)..first.col)
                ) {
                    distance += colDistance(map, colIdx)
                }
                distances += distance
            }
        }
        return distances
    }

    override fun onLines(lines: List<String>) {
        val map = lines.map { it.toCharArray().toTypedArray() }.toTypedArray()

        result = countDistances(map)
    }


    override fun onResult(): String = "${this.result}"
}

fun main() {
    Day11lvl1().start()
}