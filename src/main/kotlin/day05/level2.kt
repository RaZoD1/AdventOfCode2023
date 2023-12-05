package day05

import Level
import java.util.SortedMap


class Day05lvl2() : Level("/day05/input.txt"){

    private var min = Long.MAX_VALUE;

    fun sectionToMap(section: String): SortedMap<Long, Mapping> {
        val mapLines = section.split("\n").toMutableList().apply { removeFirst() }
        return mapLines.map { line ->
            val (destStart, sourceStart, range) = line.split(" ").map { it.toLong() }
            return@map Mapping(sourceStart, destStart, range);
        }.associateBy { it.sourceStart }.toSortedMap()
    }

    override fun onText(text: String) {
        val sections = text.split("\n\n").map { it.trim() }.filterNot { it.isEmpty() }

        val seeds = sections.first().split(": ").last().split(" ").map { it.toLong() }

        val seedRanges = mutableListOf<Pair<Long, Long>>();
        for(i in seeds.indices step 2){
            seedRanges.add(Pair(seeds[i], seeds[i+1]))
        }

        val maps = sections.toMutableList().apply { removeFirst() }.map { sectionToMap(it) }

        for ((baseSeed, range) in seedRanges) {
            println("Working on Seed $baseSeed Range: $range")
            for (seed in baseSeed..<(baseSeed+range)) {
                var currentNumber = seed;
                //                print("Seed $seed ")
                maps@for (map in maps) {

                    for(mapping in map.values){
                        if(mapping.sourceStart > currentNumber) continue@maps
                        if(mapping.covers(currentNumber)){
                            currentNumber = mapping.map(currentNumber)
                            //                            print("-> $currentNumber")
                            continue@maps
                        }
                    }
                    //                    print("-> $currentNumber")

                }
                //                print("\n")
                if(currentNumber < min ) min = currentNumber;
            }
        }

    }

    override fun onResult(): String = "${this.min}"
}

fun main(){
    Day05lvl2().start()
}