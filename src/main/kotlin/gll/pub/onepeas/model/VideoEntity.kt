package gll.pub.onepeas.model


import javax.persistence.*

@Entity
@Table(name = "video")
class VideoEntity(){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ch_friend_entry_id") val id: Long? = null

    @Column(name = "title") val title: String = ""
    @Column(name = "description") val description: String = ""
    @Column(name = "is_active") val isActive: Boolean? = null
    @Column(name = "is_accepted") val isAccepted: Boolean? = null

}