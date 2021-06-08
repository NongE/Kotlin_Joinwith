package com.study_project.joinwith.model.request

import com.study_project.joinwith.annotation.DateCheck
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class JoinRequest(

    @ApiModelProperty(position = 0, example = "input_user_id")
    @field:NotEmpty(message = "아이디는 공백일 수 없습니다.")
    var user_id: String? = null,

    @ApiModelProperty(position = 1, example = "input_user_pw")
    @field:NotEmpty(message = "비밀번호는 공백일 수 없습니다.")
    var pw: String? = null,

    @ApiModelProperty(position = 2, example = "input_user_name")
    @field:NotEmpty(message = "이름은 공백일 수 없습니다.")
    var userName: String? = null,

    @ApiModelProperty(position = 3, example = "example@example.com")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    var email: String? = null,

    @ApiModelProperty(position = 4, example = "input_user_address")
    var address: String? = null,

    @ApiModelProperty(position = 5, example = "010-1111-1111")
    @field:Pattern(regexp="^\\d{2,3}-\\d{3,4}-\\d{4}\$", message = "올바른 전화번호 형식이 아닙니다.")
    var phoneNumber: String? = null,

    @ApiModelProperty(position = 6, example = "yyyy-MM-dd")
    @field:DateCheck()
    var birth: String? = null,
) {}
