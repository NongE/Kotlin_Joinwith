package com.study_project.joinwith.repository

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.dto.ChangePasswordDto
import com.study_project.joinwith.model.dto.OverlapCheckDto
import org.springframework.data.repository.CrudRepository

interface JoinwithRepository: CrudRepository<Join, Long?>{

    fun findJoinByUserId(userId: String): List<OverlapCheckDto>

    fun findJoinByUserIdAndPw(userId: String, pw:String): List<ChangePasswordDto>

}