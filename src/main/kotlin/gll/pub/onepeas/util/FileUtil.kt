package gll.pub.onepeas.util

import com.google.gson.Gson
import gll.pub.onepeas.controller.VideoJsonBean
import java.io.File

object FileUtil {
    fun readJSON(fileName: String): String {
        return File(fileName).readText()

    }

    fun getFileNameList(json:String):List<VideoJsonBean>{
        val strList = arrayListOf<String>(".mkv",".mp4",".flv",".rmvb")
        val json = FileUtil.readJSON("src/main/resources/$json.json").replace("last modified","lastModified")
        val list = JsonUtils.fromJsonList<JSON>(json,JSON::class.java)
        val resultList = arrayListOf<VideoJsonBean>()
        list?.forEach {
            val name = it.name.run {
                var replaceStr:String?=null
                strList.forEach {
                    if (this.contains(it,true)){
                        replaceStr = this.replace(it,"",true)
                    }
                }
                replaceStr
            }
            if (name!=null){
                resultList.add(VideoJsonBean(name,it.name,it.lastModified,it.size))
            }
        }
        return resultList
    }
}

fun main() {
    val strList = arrayListOf<String>(".mkv",".mp4",".flv",".rmvb")
    val json = FileUtil.readJSON("src/main/resources/data.json").replace("last modified","lastModified")
    val list = JsonUtils.fromJsonList<JSON>(json,JSON::class.java)
    list?.forEach {
        val name = it.name.run {
            var replaceStr:String?=null
            strList.forEach {
                if (this.contains(it,true)){
                    replaceStr = this.replace(it,"",true)
                }
            }
            replaceStr
        }
        if (name!=null){
            println(name)
        }
    }
}
data class JSON(val name:String,val lastModified:String,val size:String)