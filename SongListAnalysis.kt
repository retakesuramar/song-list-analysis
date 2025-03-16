// .................................................................

fun main() {
    val whichOptionToRun = 3
    when(whichOptionToRun) {
        1 -> simpleCheck(::containsExactlyOneCaratIfNecessary)
        2 -> simpleCheck(::doesNotContainEmptyFootnote)
        3 -> simpleCheck(::footnoteWithinRange)
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

private fun footnoteWithinRange(line: String, toInstall: Boolean): Boolean {
    if (line.isBlank()) return true
    if (line.startsWith("*(")) return true
    val penultimateChar = line[line.length - 2]
    val previous = line[line.length - 3]
    val footnoteIndex = if (previous == '^')
        penultimateChar.toString().toIntOrNull()
    else
        (previous.toString() + penultimateChar.toString()).toIntOrNull()
    if (footnoteIndex == null) return false
    if (footnoteIndex < 0) return false
    if (toInstall)
        return footnoteIndex <= 7
    else
        return footnoteIndex <= 19
}
