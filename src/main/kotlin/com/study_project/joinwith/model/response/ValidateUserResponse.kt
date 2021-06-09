package com.study_project.joinwith.model.response

data class ValidateUserResponse(
    var path: String? = null,

    var resultCode: String? = null,

    var httpStatus: String? = null,

    var message: String? = null,
)