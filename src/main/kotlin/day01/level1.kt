package day01

import getResourceAsText


fun getCalibrationValue(line: String): Int {
    var start = 0;
    var end = line.length - 1;

    while(start != end){
        println("doing: $line $start $end")
        val startChar = line[start]
        val endChar = line[end]

        if(!startChar.isDigit()) start++;
        if(!endChar.isDigit()) end--;

        if(startChar.isDigit() && endChar.isDigit()) break;
    }

    val result = "${line[start]}${line[end]}".toInt();
    return result;
}

fun main(){
    println("Hey")
    val text = getResourceAsText("/day01/input1.txt")
    if(text == null) {
        println("Resource Null")
        return
    }
    println(text)
    val results = text.split("\n").map { getCalibrationValue(it) }

    println(results.sum())
}