package com.study_project.joinwith.model.request

import io.swagger.annotations.ApiModelProperty

data class DeleteUserRequest(
    @ApiModelProperty(position = 0, example = "input_delete_user_id")
    var userId: String? = null,
    @ApiModelProperty(position = 1, example = "input_delete_user_pw")
    var pw:String? = null
){}