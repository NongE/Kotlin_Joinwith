package com.study_project.joinwith.service


import com.study_project.joinwith.model.request.ChangePasswordRequest
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional

class JoinServiceTest(
    private val joinwithRepository: JoinwithRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val joinService = JoinService(joinwithRepository, passwordEncoder)


    @Test
    fun saveTest() {
        // given
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = "JoinTestPw"
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }
        // when
        val result = joinService.save(userData)

        // then
        Assertions.assertEquals(true, result)
    }

    @Test
    fun overlapCheckTest() {
        // given
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = "JoinTestPw"
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }
        joinService.save(userData)

        // when
        val overlapId = joinService.overlapCheck("JoinTestID")
        val notOverlapId = joinService.overlapCheck("JoinTestID_not_overlap")

        // then
        Assertions.assertEquals(true, overlapId)
        Assertions.assertEquals(false, notOverlapId)
    }

    @Test
    fun changePasswordTest() {
        // given
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = "JoinTestPw"
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }
        joinService.save(userData)

        // when
        val userDataCorrectPw = ChangePasswordRequest("JoinTestID", "JoinTestPw", "JoinTestChangedPw")
        val userDataNotCorrectPw = ChangePasswordRequest("JoinTestID", "JoinTestPw", "JoinTestChangedPw")
        val userDataNotExistId = ChangePasswordRequest("NotExistJoinTestID", "NotExistJoinTestPw", "NotExistJoinTestChangedPw")

        val result = joinService.changePassword(userDataCorrectPw)
        val resultNotCorrectPw = joinService.changePassword(userDataNotCorrectPw)
        val resultNotExistId = joinService.changePassword(userDataNotExistId)

        // then
        Assertions.assertEquals(true, result)
        Assertions.assertEquals(false, resultNotCorrectPw)
        Assertions.assertEquals(false, resultNotExistId)


    }

    @Test
    fun validateUserTest() {
        // given
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = "JoinTestPw"
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }
        joinService.save(userData)

        // when
        val userWithChangedPw = ValidateUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestPw"
        }
        val resultWithChangedPw = joinService.validateUser(userWithChangedPw)

        val userWithNotChangedPw = ValidateUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestChangedPw"
        }
        val resultWithNotChangedPw = joinService.validateUser(userWithNotChangedPw)

        // then
        Assertions.assertEquals(true, resultWithChangedPw)
        Assertions.assertEquals(false, resultWithNotChangedPw)
    }

}