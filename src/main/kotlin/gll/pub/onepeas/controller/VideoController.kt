package gll.pub.onepeas.controller

import com.google.gson.Gson
import gll.pub.onepeas.model.VideoEntity
import gll.pub.onepeas.repository.VideoRepository
import gll.pub.onepeas.util.FileUtil
import gll.pub.onepeas.util.JsonUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

import java.util.Optional

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import javax.persistence.criteria.*

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
        val pages = userRepository.findAll(object :Specification<VideoEntity>{
            override fun toPredicate(root: Root<VideoEntity>, p1: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder): Predicate? {
                val userName: Path<String> = root["url"]
                return criteriaBuilder.like(userName, "%${key}%")
            }
        },pageable)
        println("查询总页数："+pages.totalPages)
        println("查询总记录数："+pages.totalElements)
        println("查询当前第几页："+pages.number +1);// 注意 0开始
        println("查询当前页面的集合："+pages.content)
        println("查询当前页面的记录数："+pages.numberOfElements);
        return pages
    }

    fun save(videoEntityList:List<VideoJsonBean>,path: String){
        videoEntityList.forEach {
            val videoEntity = VideoEntity()
            videoEntity.title = it.name
            if (path==""){
                videoEntity.url = "movies/${it.url}"
            }else{
                videoEntity.url = "movies/$path/${it.url}"
            }
            videoEntity.size = it.size
            userRepository.save(videoEntity)
        }
    }

    @GetMapping("/addAll")
    suspend fun addAll(): Page<VideoEntity> {
//        val bean = FileUtil.getFileNameList("影视总")
//        save(bean,"")
//
//        val bean1 = FileUtil.getFileNameList("灵魂摆渡-第一季")
//        save(bean1,"灵H摆D 1-3/第一季")
//
//        val bean2 = FileUtil.getFileNameList("灵魂摆渡-第二季")
//        save(bean2,"灵H摆D 1-3/第二季")
//
//        val bean3 = FileUtil.getFileNameList("灵魂摆渡-第三季")
//        save(bean3,"灵H摆D 1-3/第三季")
//
//        val bean4 = FileUtil.getFileNameList("灵魂摆渡-黄泉")
//        save(bean4,"灵H摆D 1-3/黄泉")
//
//        val bean5 = FileUtil.getFileNameList("秦时明月-百步飞剑")
//        save(bean5,"秦时明月(2007) [主系列六部全+天行九歌]/百步飞剑")
//
//        val bean6 = FileUtil.getFileNameList("秦时明月-沧海横流")
//        save(bean6,"秦时明月(2007) [主系列六部全+天行九歌]/沧海横流")
//
//        val bean7 = FileUtil.getFileNameList("秦时明月-君临天下")
//        save(bean7,"秦时明月(2007) [主系列六部全+天行九歌]/君临天下")
//
//        val bean8 = FileUtil.getFileNameList("秦时明月-天行九歌")
//        save(bean8,"秦时明月(2007) [主系列六部全+天行九歌]/天行九歌")
//
//        val bean9 = FileUtil.getFileNameList("秦时明月-万里长城")
//        save(bean9,"秦时明月(2007) [主系列六部全+天行九歌]/万里长城")
//
//        val bean10 = FileUtil.getFileNameList("秦时明月-夜尽天明")
//        save(bean10,"秦时明月(2007) [主系列六部全+天行九歌]/夜尽天明")
//
//        val bean11 = FileUtil.getFileNameList("秦时明月-诸子百家")
//        save(bean11,"秦时明月(2007) [主系列六部全+天行九歌]/诸子百家")
//
//        val bean12 = FileUtil.getFileNameList("职场")
//        save(bean12,"职场生存指南：深度拆解困住1000万人的52个职场难题（完结）")
//
//        val bean13 = FileUtil.getFileNameList("鬼喊抓鬼")
//        save(bean13,"G.鬼喊抓鬼 牛大宝（全227集）")


        val order1 = Sort.Order(Sort.Direction.DESC, "id")
        val sort = Sort.by(arrayOf(order1).toMutableList())
        val pageable: PageRequest = PageRequest.of(0, 1000, sort);
        val pages = userRepository.findAll(pageable);
        println("查询总页数："+pages.totalPages)
        println("查询总记录数："+pages.totalElements)
        println("查询当前第几页："+pages.number +1);// 注意 0开始
        println("查询当前页面的集合："+pages.content)
        println("查询当前页面的记录数："+pages.numberOfElements);
        return pages
    }
    @GetMapping("/add")
    suspend fun add(json: String, path: String): Page<VideoEntity> {
//        val bean = FileUtil.getFileNameList(json)
//        bean.forEach {
//            val videoEntity = VideoEntity()
//            videoEntity.title = it.name
//            if (path==""){
//                videoEntity.url = "movies/${it.url}"
//            }else{
//                videoEntity.url = "movies/$path/${it.url}"
//            }
//            videoEntity.size = it.size
//            userRepository.save(videoEntity)
//        }




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
data class VideoJsonBean(val name:String,val url:String,val lastModified:String,val size:String)
