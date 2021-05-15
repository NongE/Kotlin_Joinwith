package com.study_project.joinwith.model.request

data class ValidateUserRequest(
    var userId: String? = null,
    var pw:String? = null
){}