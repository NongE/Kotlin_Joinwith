package com.study_project.joinwith.repository

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.ChangePasswordDto
import com.study_project.joinwith.model.JoinRequest
import com.study_project.joinwith.model.OverlapCheckDto
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JoinwithRepository: CrudRepository<Join, Long?>{

    fun findJoinByUserId(userId: String): List<OverlapCheckDto>

    fun findJoinByUserIdAndPw(userId: String, pw:String): List<ChangePasswordDto>

}