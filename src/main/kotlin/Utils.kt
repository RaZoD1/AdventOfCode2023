fun getResourceAsText(path: String): String? =
    object {}.javaClass.getResource(path)?.readText()

class InputFile(val path: String){
    private val rawText = getResourceAsText(path);
    init {
        if(rawText == null ) throw Exception("Resource $path is null")
    }

    val text = rawText!!.trim().replace("\r", "")
    val lines = text.split("\n").filterNot {it.isEmpty()}
}

abstract class Level(val path: String){

    val inputFile = InputFile(path)

    fun start(){
        onInit()
        onText(inputFile.text)
        onLines(inputFile.lines)
        inputFile.lines.forEach { onLine(it) }
        println(onResult())
    }

    open fun onInit(){}
    open fun onText(text: String){}
    open fun onLines(lines: List<String>){}
    open fun onLine(line: String){}

    abstract fun onResult(): String
}