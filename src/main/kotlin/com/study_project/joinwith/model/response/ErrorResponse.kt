package com.study_project.joinwith.model.response

data class ErrorResponse(
    var resultCode: String? = null,

    var httpStatus: String? = null,

    var httpMethod:String? = null,

    var message:String? = null,

    var path:String? = null
)