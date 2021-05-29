package com.study_project.joinwith.service

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.request.ChangePasswordRequest
import com.study_project.joinwith.model.request.DeleteUserRequest
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

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
        joinwithRepository.findJoinByUserId(validateUserRequest.userId as String).ifPresent {
            if (passwordEncoder.matches(validateUserRequest.pw, it.getPw())) {
                validateFlag = true
            }
        }
        return validateFlag
    }

    fun changePassword(changePasswordRequest: ChangePasswordRequest): Boolean {
        var passwordChangeFlag: Boolean = false
        passwordChangeFlag = validateAndPassword(changePasswordRequest, passwordChangeFlag)

        return passwordChangeFlag
    }

    private fun validateAndPassword(
        changePasswordRequest: ChangePasswordRequest,
        passwordChangeFlag: Boolean,
    ): Boolean {
        var passwordChangeFlag1 = passwordChangeFlag
        joinwithRepository.findJoinByUserId(changePasswordRequest.userId).ifPresent {
            val isValidateUser =
                ValidateUserRequest(changePasswordRequest.userId, changePasswordRequest.presentPassword)
            if (validateUser(isValidateUser)) {
                joinwithRepository.findById(it.getId().toLong()).ifPresent { userInfo ->
                    userInfo.pw = passwordEncoder.encode(changePasswordRequest.changedPassword)
                    joinwithRepository.save(userInfo)
                    passwordChangeFlag1 = true
                }
            }
        }
        return passwordChangeFlag1
    }

    fun deleteUser(deleteUserRequest: DeleteUserRequest): Boolean {
        var deleteUserFlag: Boolean = false
        deleteUserFlag = validateAndDelete(deleteUserRequest, deleteUserFlag)

        return deleteUserFlag
    }

    private fun validateAndDelete(
        deleteUserRequest: DeleteUserRequest,
        deleteUserFlag: Boolean,
    ): Boolean {
        var deleteUserFlag1 = deleteUserFlag
        joinwithRepository.findJoinByUserId(deleteUserRequest.userId as String).ifPresent {
            val isValidateUser = ValidateUserRequest(deleteUserRequest.userId, deleteUserRequest.pw)
            if (validateUser(isValidateUser)) {
                joinwithRepository.findById(it.getId().toLong()).ifPresent { userInfo ->
                    joinwithRepository.deleteById(userInfo.id as Long)
                    deleteUserFlag1 = true
                }
            }
        }
        return deleteUserFlag1
    }
}