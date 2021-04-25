package com.study_project.joinwith.service

import com.study_project.joinwith.model.Join
import com.study_project.joinwith.repository.JoinwithRepository
import org.springframework.stereotype.Service

@Service
class JoinService(
    val joinwithRepository: JoinwithRepository
) {

    fun save(join: Join){
        joinwithRepository.save(join)
    }

    fun find(): MutableIterable<Join> {
        return joinwithRepository.findAll()
    }
}