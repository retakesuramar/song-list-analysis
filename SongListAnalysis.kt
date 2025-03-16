// .................................................................

fun main() {
    val whichOptionToRun = 1
    when(whichOptionToRun) {
        1 -> simpleCheck(::containsExactlyOneCaratIfNecessary)
        2 -> simpleCheck(::footnoteValid)
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

private fun footnoteValid(line: String, toInstall: Boolean): Boolean {
    if (line.isBlank()) return true
    if (line.startsWith("*(")) return true
    val footnote = line.substringAfterLast(" ")
    if (footnote[0] != '(') return false
    if (footnote[1] != '^') return false
    if (!footnote.endsWith(")")) return false
    
    val footnoteIndex = footnote.substring(2, footnote.length - 1).toIntOrNull()
    if (footnoteIndex == null) return false
    if (footnoteIndex < 0) return false
    if (toInstall)
        return footnoteIndex <= 7
    else
       return footnoteIndex <= 19
}
