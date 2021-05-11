package com.study_project.joinwith.repository

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.dto.ChangePasswordDto
import com.study_project.joinwith.model.dto.OverlapCheckDto
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JoinwithRepository: CrudRepository<Join, Long?>{

    fun findJoinByUserId(userId: String): Optional<OverlapCheckDto>

    fun findJoinByUserIdAndPw(userId: String, pw:String): Optional<ChangePasswordDto>

}