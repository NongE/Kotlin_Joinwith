package com.study_project.joinwith.service

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.dto.OverlapCheckDto
import com.study_project.joinwith.model.request.ChangePasswordRequest
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
class JoinService(
    val joinwithRepository: JoinwithRepository,
    val passwordEncoder: PasswordEncoder,
) {

    fun find(): MutableIterable<Join> {
        return joinwithRepository.findAll()
    }

    fun overlapCheck(userId: String): Boolean {
        return joinwithRepository.findJoinByUserId(userId).isPresent
    }

    fun save(joinRequest: JoinRequest): Boolean {
        joinRequest.let {
            it.pw = passwordEncoder.encode(it.pw)
            Join().convertJoin(it)
        }.let {
            joinwithRepository.save(it)
            return true
        }
    }

    fun validateUser(validateUserRequest: ValidateUserRequest): Boolean {
        var validateFlag = false
        joinwithRepository.findJoinByUserId(validateUserRequest.userId).ifPresent {
            if (passwordEncoder.matches(validateUserRequest.pw, it.getPw())){
                validateFlag = true
            }
        }
        return validateFlag
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest): Boolean {
        var passwordChangeFlag: Boolean = false
        joinwithRepository.findJoinByUserIdAndPw(changePasswordRequest.userId, changePasswordRequest.presentPassword)
            .ifPresent {
                joinwithRepository.findById(it.getId().toLong()).ifPresent { userInfo ->
                    userInfo.pw = changePasswordRequest.changedPassword
                    joinwithRepository.save(userInfo)
                }
                passwordChangeFlag = true
            }

        return passwordChangeFlag
    }

    fun deleteUser(validateUserRequest: ValidateUserRequest): Boolean {
        var deleteUserFlag: Boolean = false
        joinwithRepository.findJoinByUserIdAndPw(validateUserRequest.userId, validateUserRequest.pw).ifPresent {
            joinwithRepository.deleteById(it.getId().toLong())
            deleteUserFlag = true
        }
        return deleteUserFlag
    }
}