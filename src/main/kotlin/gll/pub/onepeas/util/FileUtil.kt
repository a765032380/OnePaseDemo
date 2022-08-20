package gll.pub.onepeas.util

import java.io.File

object FileUtil {
    fun readJSON(fileName: String): String {
        return File(fileName).readText()

    }
}

fun main() {
    val json = FileUtil.readJSON("src/main/resources/data.json").replace("last modified","lastModified")
    println()
}
data class JSON(val name:String,val lastModified:String,val size:String)