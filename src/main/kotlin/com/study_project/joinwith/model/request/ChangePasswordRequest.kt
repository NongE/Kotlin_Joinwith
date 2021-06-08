package com.study_project.joinwith.model.request

import io.swagger.annotations.ApiModelProperty

data class ChangePasswordRequest(
    // Swagger 의 Example Value 에 예시 값을 넣어줄 수 있음
    @ApiModelProperty(position = 0, example = "input_user_id")
    var userId: String,
    @ApiModelProperty(position = 1, example = "input_user_present_pw")
    var presentPassword: String,
    @ApiModelProperty(position = 2, example = "input_user_changed_pw")
    var changedPassword: String
) {
}