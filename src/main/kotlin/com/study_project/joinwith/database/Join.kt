package com.study_project.joinwith.database


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name="user")
data class Join(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    var user_id: String? = null,

    var pw: String? = null,

    @Column(name = "user_name")
    var userName: String? = null,

    // 메일 검증 필요
    @Column(name = "e_mail")
    var eMail: String? = null,

    var address: String? = null,

    // 번호 검증 필요
    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    // 날짜 검증 필요
    var birth: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
) {}