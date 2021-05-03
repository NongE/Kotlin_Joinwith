package com.study_project.joinwith.service

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.ChangePasswordRequest
import com.study_project.joinwith.model.JoinRequest
import com.study_project.joinwith.model.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.springframework.stereotype.Service

@Service
class JoinService(
    val joinwithRepository: JoinwithRepository,
) {

    fun find(): MutableIterable<Join> {
        return joinwithRepository.findAll()
    }

    fun overlapCheck(userId: String): Boolean {
        return joinwithRepository.findJoinByUserId(userId).isNotEmpty()
    }

    fun save(joinRequest: JoinRequest) {
        joinRequest.let {
            Join().convertJoin(it)
        }.let {
            joinwithRepository.save(it)
        }
    }

    fun validateUser(validateUserRequest: ValidateUserRequest): Boolean {
        val validateResult = joinwithRepository.findJoinByUserIdAndPw(validateUserRequest.userId, validateUserRequest.pw)
        return validateResult.isNotEmpty()
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest): Boolean {
        val validateUser = joinwithRepository.findJoinByUserIdAndPw(changePasswordRequest.userId, changePasswordRequest.presentPassword)
        return if (validateUser.isNotEmpty()){
            joinwithRepository.findById(validateUser[0].getId().toLong()).ifPresent {
                it.pw = changePasswordRequest.changedPassword
                joinwithRepository.save(it)

            }
            true
        } else{
            false
        }
    }
}