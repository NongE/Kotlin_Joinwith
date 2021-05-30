package com.study_project.joinwith.database


import com.study_project.joinwith.model.request.JoinRequest
import javax.persistence.*

@Entity
@Table(name = "user")
data class Join(
    // pk는 auto increment 활용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var userId: String? = null,

    var pw: String? = null,

    // snake case 변경
    @Column(name = "user_name")
    var userName: String? = null,

    var email: String? = null,

    var address: String? = null,

    // snake case 변경
    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    var birth: String? = null,
)

// DTO 로 변환하기 위한 함수
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