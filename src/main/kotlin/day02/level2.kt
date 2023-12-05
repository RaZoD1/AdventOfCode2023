package day02

import getResourceAsText

fun ColorCombo.power(): Int = this.red * this.green* this.blue

fun mergeColorCombos(combos: List<ColorCombo>): ColorCombo {
    var (red, green, blue) = listOf(0,0,0);

    for (combo in combos) {
        if(combo.red > red) red = combo.red
        if(combo.green > green) green = combo.green
        if(combo.blue > blue) blue = combo.blue
    }
    return ColorCombo(red, green, blue)
}

fun getPower(line: String): Int{
    val (game, setsRaw) = line.split(":");

    val gameId = game.split(" ").last().toInt()

    val sets = setsRaw.trim().split(";").map { it.toColorCombo() }
    return mergeColorCombos(sets).power()
}

fun main(){
    println("Hey")
    val text = getResourceAsText("/day02/input.txt")?.trim()
    if(text == null) {
        println("Resource Null")
        return
    }
    println(text)
    val results = text.split("\n").filter { it.isNotEmpty() }.map { getPower(it) }

    println(results.sum())
}