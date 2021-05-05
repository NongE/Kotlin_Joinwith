package com.study_project.joinwith.model.request

data class JoinRequest(
    var user_id: String? = null,

    var pw: String? = null,

    var userName: String? = null,

    var email: String? = null,

    var address: String? = null,

    // 핸드폰 번호 검증 필요
    var phoneNumber: String? = null,

    // 날짜 검증 필요
    var birth: String? = null,
) {}
