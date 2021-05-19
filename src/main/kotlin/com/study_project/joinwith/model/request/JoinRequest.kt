package com.study_project.joinwith.model.request

import com.study_project.joinwith.annotation.DateCheck
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class JoinRequest(

    @field:NotEmpty(message = "아이디는 공백일 수 없습니다.")
    var user_id: String? = null,

    @field:NotEmpty(message = "비밀번호는 공백일 수 없습니다.")
    var pw: String? = null,

    @field:NotEmpty(message = "이름은 공백일 수 없습니다.")
    var userName: String? = null,

    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    var email: String? = null,

    var address: String? = null,

    @field:Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}\$", message = "올바른 전화번호 형식이 아닙니다.")
    var phoneNumber: String? = null,

    // 날짜 검증 필요
    @field:DateCheck()
    var birth: String? = null,
) {}
