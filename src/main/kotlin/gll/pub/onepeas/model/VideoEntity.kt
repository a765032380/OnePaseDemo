package gll.pub.onepeas.model


import javax.persistence.*

@Entity
@Table(name = "video")
class VideoEntity(){

    @Id
    @GeneratedValue()
    @Column(name = "id") val id: Long? = null

    @Column(name = "title") var title: String = ""
    @Column(name = "description") var description: String = ""
    @Column(name = "cover") var cover: String = ""
    @Column(name = "size") var size: String = ""
    @Column(name = "url") var url: String = ""

}