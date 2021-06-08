package com.study_project.joinwith.repository

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.dto.ChangePasswordDto
import com.study_project.joinwith.model.dto.OverlapCheckDto
import org.springframework.data.repository.CrudRepository
import java.util.*

interface JoinwithRepository: CrudRepository<Join, Long?>{

    fun findByUserId(userId: String): Optional<OverlapCheckDto>

    fun findByUserIdAndPw(userId: String, pw:String): Optional<ChangePasswordDto>

}