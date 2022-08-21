package gll.pub.onepeas.util

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import gll.pub.onepeas.util.JsonUtils.fromJson
import java.util.*


object JsonUtils {

    /**
     * fromJson2List
     */
    inline fun <reified T> fromJson2List(json: String) = fromJson<List<T>>(json)

    /**
     * fromJson
     */
    inline fun <reified T> fromJson(json: String): T? {
        return try {
            val type = object : TypeToken<T>() {}.type
            return Gson().fromJson(json, type)
        } catch (e: Exception) {
            println("try exception,${e.message}")
            null
        }
    }
    inline fun <T> fromJsonList(json: String?, cls: Class<T>?): ArrayList<T>? {
        val mList = ArrayList<T>()
        val array: JsonArray = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            mList.add(Gson().fromJson(elem, cls))
        }
        return mList
    }
}