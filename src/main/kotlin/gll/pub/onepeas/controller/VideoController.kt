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
        println("??????????????????"+pages.totalPages)
        println("?????????????????????"+pages.totalElements)
        println("????????????????????????"+pages.number +1);// ?????? 0??????
        println("??????????????????????????????"+pages.content)
        println("?????????????????????????????????"+pages.numberOfElements);
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
//        val bean = FileUtil.getFileNameList("?????????")
//        save(bean,"")
//
//        val bean1 = FileUtil.getFileNameList("????????????-?????????")
//        save(bean1,"???H???D 1-3/?????????")
//
//        val bean2 = FileUtil.getFileNameList("????????????-?????????")
//        save(bean2,"???H???D 1-3/?????????")
//
//        val bean3 = FileUtil.getFileNameList("????????????-?????????")
//        save(bean3,"???H???D 1-3/?????????")
//
//        val bean4 = FileUtil.getFileNameList("????????????-??????")
//        save(bean4,"???H???D 1-3/??????")
//
//        val bean5 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean5,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean6 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean6,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean7 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean7,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean8 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean8,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean9 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean9,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean10 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean10,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean11 = FileUtil.getFileNameList("????????????-????????????")
//        save(bean11,"????????????(2007) [??????????????????+????????????]/????????????")
//
//        val bean12 = FileUtil.getFileNameList("??????")
//        save(bean12,"???????????????????????????????????????1000?????????52???????????????????????????")
//
//        val bean13 = FileUtil.getFileNameList("????????????")
//        save(bean13,"G.???????????? ???????????????227??????")


        val order1 = Sort.Order(Sort.Direction.DESC, "id")
        val sort = Sort.by(arrayOf(order1).toMutableList())
        val pageable: PageRequest = PageRequest.of(0, 1000, sort);
        val pages = userRepository.findAll(pageable);
        println("??????????????????"+pages.totalPages)
        println("?????????????????????"+pages.totalElements)
        println("????????????????????????"+pages.number +1);// ?????? 0??????
        println("??????????????????????????????"+pages.content)
        println("?????????????????????????????????"+pages.numberOfElements);
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
        println("??????????????????"+pages.totalPages)
        println("?????????????????????"+pages.totalElements)
        println("????????????????????????"+pages.number +1);// ?????? 0??????
        println("??????????????????????????????"+pages.content)
        println("?????????????????????????????????"+pages.numberOfElements);
        return pages
    }

}
data class VideoJsonBean(val name:String,val url:String,val lastModified:String,val size:String)
