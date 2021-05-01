package com.study_project.joinwith.model

data class ChangePasswordRequest(
    var userId: String,
    var presentPassword: String,
    var changedPassword: String
) {
}