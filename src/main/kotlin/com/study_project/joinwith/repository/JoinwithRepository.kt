package com.study_project.joinwith.repository

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.OverlapCheckDto
import org.springframework.data.repository.CrudRepository

interface JoinwithRepository: CrudRepository<Join, String?>{

    fun findJoinByUserId(userId: String): List<OverlapCheckDto>

}