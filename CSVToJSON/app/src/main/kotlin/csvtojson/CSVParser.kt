
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

fun parseCsv(csv:String) {
    val log = mutableListOf(Token("sep"))

    for (letter in csv) {
        val last = log.last()
        val str_letter = letter.toString()

        when (last.type) {
            "quotedstring" -> {
                if (letter in ",\n" && last.tailQuots() % 2 == 1) {
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

    var fields = mutableListOf<String>()
    var v:String = ""

    for (token in log) {
        if (token.type == "sep") {
            fields.add(v)
            v = ""

            if (token.value == "\n") {
                println(fields)
                fields.clear()
            }
        } else {
            v = token.value
        }
    }
}
