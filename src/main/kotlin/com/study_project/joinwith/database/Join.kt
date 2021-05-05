package com.study_project.joinwith.database


import com.study_project.joinwith.model.request.JoinRequest
import javax.persistence.*

@Entity
@Table(name = "user")
data class Join(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: String? = null,

    var pw: String? = null,

    @Column(name = "user_name")
    var userName: String? = null,

    var email: String? = null,

    var address: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    var birth: String? = null,
)

fun Join.convertJoin(joinRequest: JoinRequest): Join {
    return Join().apply {
        this.userId = joinRequest.user_id
        this.pw = joinRequest.pw
        this.userName = joinRequest.userName
        this.email = joinRequest.email
        this.address = joinRequest.address
        this.phoneNumber = joinRequest.phoneNumber
        this.birth = joinRequest.birth
    }
}