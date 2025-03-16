// .................................................................

fun main() {
    val whichOptionToRun = 1
    when(whichOptionToRun) {
        1 -> doesEveryLineContainExactlyOneCarat()
        else -> println("Unknown option")
    }
    println("Program ended")
}

private fun doesEveryLineContainExactlyOneCarat() {
    var result = true
    for (line in TO_INSTALL.split("\n")) {
        if (line.isBlank()) continue
        if (line.startsWith("*(")) continue
        if (line.count{it == '^'} != 1) {
            println(line)
            result = false
        }
    }
    for (line in NOT_TO_INSTALL.split("\n")) {
        if (line.isBlank()) continue
        if (line.startsWith("*(")) continue
        if (line.count{it == '^'} != 1) {
            println(line)
            result = false
        }
    }
    println("result: $result")
}
 
