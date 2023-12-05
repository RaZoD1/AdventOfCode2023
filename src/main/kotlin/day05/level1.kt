package day05

import Level
import day04.ScratchCard
import java.util.SortedMap

data class Mapping(val sourceStart: Long, val destinationStart: Long, val range: Long){
    fun covers(num: Long): Boolean = num >= sourceStart && num < sourceStart + range

    fun map(num: Long): Long = if(covers(num)) num - sourceStart + destinationStart else num;

}

class Day05lvl1() : Level("/day05/input.txt"){

    private var min = Long.MAX_VALUE;
    lateinit var seeds: List<Long>;

    fun sectionToMap(section: String): SortedMap<Long, Mapping> {
        val mapLines = section.split("\n").toMutableList().apply { removeFirst() }
        return mapLines.map { line ->
            val (destStart, sourceStart, range) = line.split(" ").map { it.toLong() }
            return@map Mapping(sourceStart, destStart, range);
        }.associateBy { it.sourceStart }.toSortedMap()
    }

    override fun onText(text: String) {
        val sections = text.split("\n\n").map { it.trim() }.filterNot { it.isEmpty() }

        seeds = sections.first().split(": ").last().split(" ").map { it.toLong() }

        val seedToSoil = sectionToMap(sections[1])
        val soilToFertilizer = sectionToMap(sections[2])
        val fertilizerToWater = sectionToMap(sections[3])
        val waterToLight = sectionToMap(sections[4])
        val lightToTemperature = sectionToMap(sections[5])
        val temperatureToHumidity = sectionToMap(sections[6])
        val humidityToLocation = sectionToMap(sections[7])

        val maps = listOf(seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation)

        min = seeds.map {seed ->
            var currentNumber = seed;
            print("Seed $seed ")
            maps@for (map in maps) {

                for(mapping in map.values){
                    if(mapping.covers(currentNumber)){
                        currentNumber = mapping.map(currentNumber)
                        print("-> $currentNumber")
                        continue@maps
                    }
                }
                print("-> $currentNumber")

            }
            print("\n")
            return@map currentNumber;
        }.min()

    }

    override fun onResult(): String = "${this.min}"
}

fun main(){
    Day05lvl1().start()
}