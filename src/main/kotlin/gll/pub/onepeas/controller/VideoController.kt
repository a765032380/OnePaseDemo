package gll.pub.onepeas.controller

import gll.pub.onepeas.model.VideoEntity
import gll.pub.onepeas.repository.VideoRepository
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
@RequestMapping("/api", produces = ["application/json"])
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
    suspend fun getVideoList(pageNo:Int): Page<VideoEntity> {
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
