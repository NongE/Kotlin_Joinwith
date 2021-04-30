package com.study_project.joinwith.service

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.JoinRequest
import com.study_project.joinwith.model.OverlapCheckDto
import com.study_project.joinwith.model.OverlapCheckRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.springframework.stereotype.Service

@Service
class JoinService(
    val joinwithRepository: JoinwithRepository
) {

    fun find(): MutableIterable<Join> {
        return joinwithRepository.findAll()
    }

    fun save(joinRequest: JoinRequest){
        joinRequest.let{
            Join().convertJoin(it)
        }.let {
            joinwithRepository.save(it)
        }
    }

    fun overlapCheck(userId: String): Boolean {
        return joinwithRepository.findJoinByUserId(userId).isNotEmpty()

    }
}