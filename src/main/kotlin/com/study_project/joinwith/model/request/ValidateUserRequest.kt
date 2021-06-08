package com.study_project.joinwith.model.request

import io.swagger.annotations.ApiModelProperty

data class ValidateUserRequest(
    @ApiModelProperty(position = 0, example = "input_user_id")
    var userId: String? = null,
    @ApiModelProperty(position = 0, example = "input_user_pw")
    var pw:String? = null
){}