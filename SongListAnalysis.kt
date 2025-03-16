// .................................................................

fun main() {
    val whichOptionToRun = 1
    when(whichOptionToRun) {
        1 -> simpleCheck(::containsExactlyOneCaratIfNecessary)
        2 -> simpleCheck(::doesNotContainEmptyFootnote)
        else -> println("Unknown option")
    }
    println("Program ended")
}

private fun simpleCheck(predicate: (String, Boolean) -> Boolean) {
    var result = true
    for (line in TO_INSTALL.split("\n")) {
        if (!predicate(line, true)) {
            println(line)
            result = false
        }
    }
    for (line in NOT_TO_INSTALL.split("\n")) {
        if (!predicate(line, false)) {
            println(line)
            result = false
        }
    }
    println("result: $result")
}

private fun containsExactlyOneCaratIfNecessary(line: String, ignored: Boolean) = line.isBlank()
    || line.startsWith("*(")
    || (line.count{it == '^'} == 1)

    
private fun doesNotContainEmptyFootnote(line: String, ignored: Boolean) = !line.contains("(^)")
