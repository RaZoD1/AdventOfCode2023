package day02

import getResourceAsText

data class ColorCombo(val red: Int, val green: Int, val blue: Int);

fun String.toColorCombo(): ColorCombo{
    val colors = this.trim().split(",").map { it.trim() };
    val red = colors.findLast { it.contains("red") }?.split(" ")?.first()?.toInt() ?: 0
    val green = colors.findLast { it.contains("green") }?.split(" ")?.first()?.toInt() ?: 0
    val blue = colors.findLast { it.contains("blue") }?.split(" ")?.first()?.toInt() ?: 0
    return ColorCombo(red, green, blue);
}

fun isGamePossible(line: String): Int{
    val (game, setsRaw) = line.split(":");

    val gameId = game.split(" ").last().toInt()

    val sets = setsRaw.trim().split(";").map { it.toColorCombo() }
    if (sets.any { it.red > 12 || it.green > 13 || it.blue > 14 }) {
        return 0;
    }
    return gameId
}

fun main(){
    println("Hey")
    val text = getResourceAsText("/day02/input.txt")?.trim()
    if(text == null) {
        println("Resource Null")
        return
    }
    println(text)
    val results = text.split("\n").filter { it.isNotEmpty() }.map { isGamePossible(it) }

    println(results.sum())
}