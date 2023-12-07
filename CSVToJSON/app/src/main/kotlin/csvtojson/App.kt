/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package csvtojson

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import app.softwork.serialization.csv.*
import kotlinx.serialization.encodeToString
import java.io.File
import java.text.SimpleDateFormat

fun androidApi(version: Double):Int {
    return when (version) {
        in 1.0..1.5 -> 1
        in 1.5..1.6 -> 3
        in 1.6..2.0 -> 4
        in 2.0..2.1 -> 5
        in 2.1..2.2 -> 7
        in 2.2..2.3 -> 8
        in 2.3..3.0 -> 9
        in 3.0..3.1 -> 11
        in 3.1..3.2 -> 12
        in 3.2..4.0 -> 13
        in 4.0..4.1 -> 14
        in 4.1..4.2 -> 16
        in 4.2..4.3 -> 17
        in 4.3..4.4 -> 18
        in 4.4..5.0 -> 19
        in 5.0..5.1 -> 21
        in 5.1..6.0 -> 22
        in 6.0..7.0 -> 23
        in 7.0..7.1 -> 24
        in 7.1..8.0 -> 25
        in 8.0..8.1 -> 26
        in 8.1..9.0 -> 27
        in 9.0..10.0 -> 28
        in 10.0..11.0 -> 29
        in 11.0..12.0 -> 30
        else -> -1
    }
}

// App,Category,Rating,Reviews,Size,Installs,Type,Price,Content Rating,Genres,Last Updated,Current Ver,Android Ver
// Photo Editor & Candy Camera & Grid & ScrapBook,ART_AND_DESIGN,4.1,159,19M,"10,000+",Free,0,Everyone,Art & Design,"January 7, 2018",1.0.0,4.0.3 and up

data class AppCSVEntry(
    val App: String,
    val Category: String,
    val Rating: String,
    val Reviews: String,
    val Size: String,
    val Installs: String,
    val Type: String,
    val Price: String,
    val ContentRating: String,
    val Genres: String,
    val LastUpdated: String,
    val CurrentVer: String,
    val AndroidVer: String
) {
    fun toSerializable():AppJSONEntry {
        val timefmt = SimpleDateFormat("MMMM d, yyyy")
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")

        return AppJSONEntry(
            this.App,
            this.Category,
            this.Rating,
            this.Reviews,
            this.Size,
            this.Installs.replace(",", "").replace("+", "").toInt(),
            this.Type != "Free",
            this.ContentRating,
            this.Genres,
            sdf.format(timefmt.parse(this.LastUpdated)),
            this.CurrentVer,
            androidApi( if (this.AndroidVer != "Varies with device") this.AndroidVer.replace(" and up", "").take(3).toDouble() else -1.0 )
        )
    }
}

@Serializable
data class AppJSONEntry(
    val App: String,
    val Category: String,
    val Rating: String,
    val Reviews: String,
    val Size: String,
    val Installs: Int,
    //val Type: String,
    val Paid:Boolean,
    val ContentRating: String,
    val Genres: String,
    val LastUpdated: String,
    val CurrentVer: String,
    val AndroidVer: Int
)

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: ./app file.csv")
        return
    }

    val raw_csv = File(args[0]).readText()
    //val result = CSVFormat.decodeFromString(ListSerializer(AppCSVEntry.serializer()), raw_csv)
    val result = parseCsv(raw_csv)

    // Заголовки
    result.remove(result.first())

    val serializable_result = result.map { it.toSerializable() }

    val json = Json.encodeToString(serializable_result)

    println("${result.size} entries loaded")
    println("${json.length} json bytes")

    File(args[0] + ".json").writeText(json)

    //println(args.size)
    //println(Token("aa", "asdasd\"\"\"").tailQuots())

    
    //parseCsv("aaa,bbb,,ccc\n")
}
