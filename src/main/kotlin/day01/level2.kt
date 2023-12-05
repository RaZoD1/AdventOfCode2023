package day01

import getResourceAsText

val digitMap: Map<String, Int> = HashMap<String, Int>().apply {
    put("one", 1)
    put("two", 2)
    put("three", 3)
    put("four", 4)
    put("five", 5)
    put("six", 6)
    put("seven", 7)
    put("eight", 8)
    put("nine", 9)
}
val digitTexts: Set<String> = digitMap.keys;

fun getCalibrationValue2(line: String): Int {
    var startNum: Int? = null;
    var endNum: Int? = null;
    var start = 0
    var end = line.length - 1;

    do {

        if (startNum == null) {
            if (line[start].isDigit())
                startNum = line[start].digitToInt()
            else {
                val substring = line.substring(start);
                for (digitText in digitTexts) {
                    if (substring.matches(Regex("^$digitText.*$"))) {
                        startNum = digitMap[digitText];
                        break;
                    }
                }
                if (startNum == null) start++;
            }

        }
        if (endNum == null) {
            if (line[end].isDigit())
                endNum = line[end].digitToInt()
            else {
                val substring = line.substring(0, end + 1);
                for (digitText in digitTexts) {
                    if (substring.matches(Regex("^.*$digitText$"))) {
                        endNum = digitMap[digitText];
                        break;
                    }
                }
                if (endNum == null) end--;
            }
        }

        if (startNum != null && endNum != null) break;
    } while (true)

    if (startNum == null || endNum == null) {
        throw Exception("Error: $startNum $endNum ; $start $end\n$line")
    }

    val result = startNum * 10 + endNum;

    println(result)
    return result;
}

fun main() {
    val text = getResourceAsText("/day01/input1.txt")
    if (text == null) {
        println("Resource Null")
        return
    }

    val results = text.split("\n").map { getCalibrationValue2(it) }

    println(results.sum())
}