package com.study_project.joinwith.model.request

data class DeleteUserRequest(
    var userId: String? = null,
    var pw:String? = null
){}