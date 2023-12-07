
package csvtojson

data class Token(val type:String = "", var value:String = "") {
    fun tailQuots():Int {
        var i = 0
        
        for (v in this.value.reversed()) {
            if (v != '"') return i
            i++
        }

        return i
    }
}

fun parseCsv(csv:String):MutableList<AppCSVEntry> {
    val log = mutableListOf(Token("sep"))

    for (letter in csv) {
        val last = log.last()
        val str_letter = letter.toString()

        when (last.type) {
            "quotedstring" -> {
                if (letter in ",\n" && last.tailQuots() % 2 == 1) {
                    last.value = last.value.dropLast(1)
                    log.add( Token("sep", str_letter) )
                } else {
                    last.value += str_letter
                }
            }
            "string" -> {
                if (letter in ",\n") {
                    log.add( Token("sep", str_letter) )
                } else {
                    last.value += str_letter
                }
            }
            "sep" -> {
                if (letter == '\"') {
                    log.add( Token("quotedstring") )
                } else if (letter in ",\n") {
                    log.add( Token("sep", str_letter) )
                } else {
                    log.add( Token("string", str_letter) )
                }
            }
            else -> {
                throw Exception("unknown token type")
            }
        }
    }

    log.remove(log.first())

    var entries = mutableListOf<AppCSVEntry>()
    var fields = mutableListOf<String>()
    var v:String = ""

    for (token in log) {
        if (token.type == "sep") {
            fields.add(v.trim())
            v = ""

            if (token.value == "\n") {
                if (fields.size != 13) {
                    println("REJECTING ${fields.size} fields line: ${fields}")
                } else {
                    val entry = AppCSVEntry(
                        fields[0],
                        fields[1],
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[5],
                        fields[6],
                        fields[7],
                        fields[8],
                        fields[9],
                        fields[10],
                        fields[11],
                        fields[12],
                    )

                    entries.add(entry)
                }

                fields.clear()
            }
        } else {
            v = token.value
        }
    }

    return entries
}
