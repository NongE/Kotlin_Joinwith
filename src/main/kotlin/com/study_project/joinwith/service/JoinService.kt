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

    // DB 모든 결과를 가져오는 함수
    fun find(): MutableIterable<Join> {
        return joinwithRepository.findAll()
    }

    // 중복 검사를 진행하는 함수
    fun overlapCheck(userId: String): Boolean {
        return joinwithRepository.findByUserId(userId).isPresent
    }

    // DB에 사용자 정보를 저장하는 함수
    fun save(joinRequest: JoinRequest): Boolean {
        joinRequest.let {
            it.pw = passwordEncoder.encode(it.pw)
            Join().convertJoin(it)
        }.let {
            joinwithRepository.save(it)
            return true
        }
    }

    // 유효한 사용자인지 검증하는 함수
    fun validateUser(validateUserRequest: ValidateUserRequest): Boolean {
        var validateFlag = false
        // 아이디를 토대로 DB 쿼리, 해당 비밀번호와 입력 받은 비밀번호가 동일한지 검증
        joinwithRepository.findByUserId(validateUserRequest.userId as String).ifPresent {
            if (passwordEncoder.matches(validateUserRequest.pw, it.getPw())) {
                // 동일하다면 true 값 반환
                validateFlag = true
            }
        }
        return validateFlag
    }

    // 비밀번호는 변경하는 함수
    fun changePassword(changePasswordRequest: ChangePasswordRequest): Boolean {
        var passwordChangeFlag: Boolean = false
        // 우선 입력받은 현재 비밀번호가 맞는지 검증 한 다음, 새로운 비밀번호로 변경함
        // 이후 결과 값을 반환
        passwordChangeFlag = validateAndPassword(changePasswordRequest, passwordChangeFlag)

        return passwordChangeFlag
    }

    // 비밀번호가 맞는지 검증, 새로운 비밀번호로 변경하는 함수
    private fun validateAndPassword(
        changePasswordRequest: ChangePasswordRequest,
        passwordChangeFlag: Boolean,
    ): Boolean {
        var passwordChangeFlag1 = passwordChangeFlag
        // 아이디를 토대로 쿼리 진행
        joinwithRepository.findByUserId(changePasswordRequest.userId).ifPresent {
            val isValidateUser =
                ValidateUserRequest(changePasswordRequest.userId, changePasswordRequest.presentPassword)
            // 유효한 사용자인지 검증, 유효하다면 true, 나머지는 false
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

    // 사용자를 삭제하는 함수
    fun deleteUser(deleteUserRequest: DeleteUserRequest): Boolean {
        var deleteUserFlag: Boolean = false
        // 비밀번호를 통해 사용자 검증 이후 정보를 삭제
        deleteUserFlag = validateAndDelete(deleteUserRequest, deleteUserFlag)

        return deleteUserFlag
    }

    private fun validateAndDelete(
        deleteUserRequest: DeleteUserRequest,
        deleteUserFlag: Boolean,
    ): Boolean {
        var deleteUserFlag1 = deleteUserFlag
        // 아이디를 토대로 검증
        joinwithRepository.findByUserId(deleteUserRequest.userId as String).ifPresent {
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