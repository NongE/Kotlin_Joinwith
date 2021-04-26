package com.study_project.joinwith.model


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.*

@Entity
@Table(name="user")
data class Join(
    @Id
    var id: String? = null,

    var pw: String? = null,

    var user_name: String? = null,

    // 메일 검증 필요
    var e_mail: String? = null,

    var address: String? = null,

    // 번호 검증 필요
    var phone_number: String? = null,

    // 날짜 검증 필요
    var birth: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"))
) {}