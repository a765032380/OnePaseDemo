package gll.pub.onepeas.controller

import com.google.gson.Gson
import gll.pub.onepeas.model.VideoEntity
import gll.pub.onepeas.repository.VideoRepository
import gll.pub.onepeas.util.FileUtil
import gll.pub.onepeas.util.JsonUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

import java.util.Optional

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/api/video", produces = ["application/json"])
class OnePeasController (private val userRepository: VideoRepository) {


    @GetMapping("/", produces = ["application/json"])
    fun getTestHtml() : String  = "<h1>" + "Hello World" + "</h1>"

    @GetMapping("/user/{id}", produces = ["application/json"])
    fun getUserById(@PathVariable("id") id: Long) : Optional<VideoEntity> = userRepository.findById(id)

    @GetMapping("/users", produces = ["application/json"])
    fun getAllUsers() : MutableIterable<VideoEntity> = userRepository.findAll()

    @GetMapping("/user/{id}/friends", produces = ["application/json"])
    fun getFriends(@PathVariable("id") id: Long) : List<VideoEntity> = userRepository.getFriendsById(id)


    @PostMapping("/user", produces = ["application/json"])
    fun addUser(newUser : VideoEntity) : VideoEntity = userRepository.save(newUser)





    @GetMapping("/list")
    suspend fun getVideoList(page:Int,key:String): Page<VideoEntity> {
//        val order1 = Sort.Order(Sort.Direction.DESC, "id")
//        val sort = Sort.by(arrayOf(order1).toMutableList())
//        val pageable: PageRequest = PageRequest.of(pageNo, 10, sort);
        val pageable: PageRequest = PageRequest.of(page-1, 10);
        val pages = userRepository.findAll(pageable);
        println("查询总页数："+pages.totalPages)
        println("查询总记录数："+pages.totalElements)
        println("查询当前第几页："+pages.number +1);// 注意 0开始
        println("查询当前页面的集合："+pages.content)
        println("查询当前页面的记录数："+pages.numberOfElements);
        return pages
    }
    @GetMapping("/add")
    suspend fun add(): Page<VideoEntity> {
        val json = FileUtil.readJSON("src/main/resources/data1.json").replace("last modified","lastModified")
        val bean = JsonUtils.fromJsonList(json,VideoJsonBean::class.java)
        bean?.forEach {
            val videoEntity = VideoEntity()
            videoEntity.title = it.name
            videoEntity.url = "http://49.232.198.163:8080/movies/${it.name}"
            videoEntity.size = it.size

            userRepository.save(videoEntity)
        }




        val order1 = Sort.Order(Sort.Direction.DESC, "id")
        val sort = Sort.by(arrayOf(order1).toMutableList())
        val pageable: PageRequest = PageRequest.of(0, 10, sort);
        val pages = userRepository.findAll(pageable);
        println("查询总页数："+pages.totalPages)
        println("查询总记录数："+pages.totalElements)
        println("查询当前第几页："+pages.number +1);// 注意 0开始
        println("查询当前页面的集合："+pages.content)
        println("查询当前页面的记录数："+pages.numberOfElements);
        return pages
    }

}
data class VideoJsonBean(val name:String,val lastModified:String,val size:String)
