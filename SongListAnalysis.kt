// .................................................................

fun main() {
    val whichOptionToRun = 1
    when(whichOptionToRun) {
        1 -> doesEveryLineContainRightCarat()
        else -> println("Unknown option")
    }
    println("Program ended")
}

private fun doesEveryLineContainRightCarat() {
    var result = true
    for (line in TO_INSTALL.split("\n")) {
        if (line.isBlank()) continue
        if (line.startsWith("*(")) continue
        if (line.count{it == '^'} > 1) {
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
 
