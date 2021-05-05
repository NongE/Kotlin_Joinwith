package com.study_project.joinwith.model.request

data class ChangePasswordRequest(
    var userId: String,
    var presentPassword: String,
    var changedPassword: String
) {
}