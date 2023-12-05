package day05

import Level
import kotlin.system.measureTimeMillis

fun Mapping.overlap(rangePair: Pair<Long, Long>):Pair<Long, Long>?{
    val (start, range ) = rangePair;
    val end = start + range;

    val overlapStart = if(start > this.sourceStart) start else this.sourceStart;
    val overlapEnd = if(end < this.sourceStart + this.range) end else this.sourceStart + this.range;

    if(overlapStart > overlapEnd) return null;
    return Pair(overlapStart, overlapEnd - overlapStart);
}
class Day05lvl2_2() : Level("/day05/input.txt"){

    private var min = Long.MAX_VALUE;

    fun sectionToMappings(section: String): List<Mapping> {
        val mapLines = section.split("\n").toMutableList().apply { removeFirst() }
        return mapLines.map { line ->
            val (destStart, sourceStart, range) = line.split(" ").map { it.toLong() }
            return@map Mapping(sourceStart, destStart, range);
        }.sortedBy { it.sourceStart }
    }

    fun performRangeMapping(ranges: MutableList<Pair<Long, Long>>, mappings: List<Mapping>): MutableList<Pair<Long, Long>> {
        val nextRanges = mutableListOf<Pair<Long, Long>>()

        while(ranges.isNotEmpty()){
            val range = ranges.removeAt(0); // get the first range and process it

            val rangeEnd = range.first + range.second;
            var mappingIdx = 0;
            // skip all mappings that are before the range
            while(mappingIdx < mappings.size && (mappings[mappingIdx].sourceStart + mappings[mappingIdx].range) < range.first){
                mappingIdx += 1;
            }

            var current = range.first;
            while(mappingIdx < mappings.size && mappings[mappingIdx].sourceStart < rangeEnd){
                val mapping = mappings[mappingIdx];

                // direct mapping from current to start of mapping
                val notMappedLen = mapping.sourceStart - current;
                if(notMappedLen > 0){
                    nextRanges.add(Pair(current, notMappedLen))
                }
                current+= notMappedLen;

                // get the overlap of the mapping and the range and map it
                val overlap = mapping.overlap(range);
                if(overlap != null){
                    nextRanges.add(Pair(mapping.map(overlap.first), overlap.second))
                    current = overlap.first + overlap.second;
                }
                mappingIdx += 1;
            }
            // map the rest of the range directly
            val notMappedLen = rangeEnd - current;
            if(notMappedLen > 0)
                nextRanges.add(Pair(current, notMappedLen))

        }
        return nextRanges;
    }

    override fun onText(text: String) {
        val sections = text.split("\n\n").map { it.trim() }.filterNot { it.isEmpty() }

        val seeds = sections.first().split(": ").last().split(" ").map { it.toLong() }

        var ranges = mutableListOf<Pair<Long, Long>>();
        for(i in seeds.indices step 2){
            ranges.add(Pair(seeds[i], seeds[i+1]))
        }

        val steps = sections.toMutableList().apply { removeFirst() }.map { sectionToMappings(it) }

        for(stepIdx in steps.indices){
            println("Step $stepIdx working on ${ranges.size} ranges")
            val mappings = steps[stepIdx];
            ranges = performRangeMapping(ranges, mappings)
        }
        min = ranges.minOf { it.first }
    }

    override fun onResult(): String = "${this.min}"
}

fun main(){
    measureTimeMillis { Day05lvl2_2().start() }.also { println("Time: ${it}ms")}
}