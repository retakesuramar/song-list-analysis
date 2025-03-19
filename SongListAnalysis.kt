// ...................................................................................................................................

fun main() {
    for (i in 1..5) {
        val result = runOption(i)
        if (!result) {
            println("Failed on $i")
        }
        if (!result) break // some weird bug with Kotlin Playground meant it didn't work if this was inside the if block
    }
    println("Program ended")
}

private fun runOption(option: Int) = when(option) {
    1 -> simpleCheck(::containsExactlyOneCaratIfNecessary)
    2 -> simpleCheck(::footnoteValid)
    3 -> simpleCheck(::originsValid)
    4 -> inAlphabeticalOrder()
    5 -> duplicated()
    else -> run {
        println("Unknown option")
        false
    }
}

private fun simpleCheck(predicate: (String, Boolean) -> Boolean): Boolean {
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
    return result
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

private fun originsValid(line: String, toInstall: Boolean): Boolean {
    if (line.isBlank()) return true
    if (line.startsWith("*(")) return true
    val withoutFootnote = line.substringBeforeLast(" ")
    val origins = "(" + withoutFootnote.substringAfterLast("(")
    
    if (!origins.endsWith(")")) return false
    if (origins.equals("(original)", ignoreCase = true)) return false
    
    val parts = origins.split(" ")
    if (parts.any{it.endsWith("-U")}) return false
    if (parts.any{it.endsWith("-SP")}) return false
    if (parts.any{it.endsWith("-Korea")}) return false
    
    if (parts.size % 2 == 0) return false
    val numOrigins = (parts.size + 1) / 2
    for (i in 1..(numOrigins-1)) {
        val slashPos = (i-1)*2 + 1
        if (parts[slashPos] != "/") return false
    }
    
    return true
}

private fun inAlphabeticalOrder(): Boolean {
    var result = inAlphabeticalOrder("install", TO_INSTALL.split("\n"))
    result = inAlphabeticalOrder("not to install", NOT_TO_INSTALL.split("\n")) && result
    return result
}

private fun inAlphabeticalOrder(desc: String, lines: List<String>): Boolean {
    val newLines = lines.map{
        var newLine = if (it.startsWith("*("))
            "*" + it.substring(2, it.length - 1)
        else it
        if (newLine.startsWith("*A ") && !newLine.startsWith("*A ("))
            newLine = "*" + newLine.substring(3)
        if (newLine.startsWith("*The "))
            newLine = "*" + newLine.substring(5)
        
        newLine.replace("(original)", "(0)")
            .replace("(main GB version)", "(main GB version 1)")
            .replace("(hard GB version)", "(main GB version 2)")
            .replace(".", "")
            .replace("Lesson By DJ", "Lesson 1 By DJ")
            .replace("5 6 7 8", "5678")
            .replace("_danger", "Danger")
            .replace("'Yap Yap'", "Yap Yap")
            .replace("*O-Ha", "*O Ha")
            .replace("Over & Over", "Over And Over")
            .replace("Club Another version", "Club zzAnother version")
            .replace("R10K", "R9K")
            .replace("Straw-buh-buh-buh-buh-berry", "Strawbuhbuhbuhbuhberry")
            .replace("*Wa Ni Natte Odorou (Kids)", "*Wa Ni Natte Odorou (0) (Kids)")
            .replace("You're", "Youre")
    }.filter{!it.isBlank()}
    for (i in 0 until newLines.size - 1) {
        if (newLines[i].compareTo(newLines[i + 1], ignoreCase = true) > 0) {
            println("$desc: The first string out of order is: ${newLines[i + 1]}")
            return false
        }
    }
    println("$desc: order okay")
    return true
}

private fun duplicated(): Boolean {
    val stripDown: (String) -> String = {
        it.substringBeforeLast("(")
            .substringBeforeLast("(")
            .replace(" (original)", "")
    }
    val notToInstallWithout = NOT_TO_INSTALL.split("\n")
        .map{stripDown(it)}
    for (line in TO_INSTALL.split("\n")) {
        val without = stripDown(line)
        
        if (notToInstallWithout.contains(without)) {
            println(line)
            return false
        }
    }
    println("no duplicates")
    return true
}
