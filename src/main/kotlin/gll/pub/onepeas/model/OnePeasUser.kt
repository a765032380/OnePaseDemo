package gll.pub.onepeas.model

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Email

@Entity
@Table(name = "ch_user")
class OnePeasUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ch_user_id") var id: Long? = null

    @Column(name = "first_name") @get: NotBlank val firstName: String = ""
    @Column(name = "last_name")  @get: NotBlank  val lastName: String = ""
    @Column(name = "email") @get: Email val email: String = ""
    @Column(name = "phone") @get: NotBlank val phone: String = ""
    @Column(name = "is_active") val isActive: Boolean? = true
    @Column(name = "image_url") val imageUrl: String
    @Column(name = "date_created") var dateCreated: Timestamp? = null
    @Column(name = "address") val address: String = ""
    @Column(name = "password") var password: String? = ""

    constructor(id: Long?, firstName: String, lastName: String,
                email: String, phone: String, isActive: Boolean?,
                imageUrl: String, dateCreated: Timestamp?, address: String, password: String?)

    constructor()

    init {
        this.imageUrl = "$firstName-$lastName.png"
        this.dateCreated = RequestTimer.getCurrentTime("yyyy-MM-dd HH:mm:ss")
    }

    @JsonIgnore
    fun getFullName() : String {
        return "$firstName $lastName"
    }

    fun formatResponse() : OnePeasUser {
        return OnePeasUser(
                this.id, this.firstName, this.lastName, this.email,
                this.phone, null, this.imageUrl, this.dateCreated,
                this.address, null
        )
    }
}
