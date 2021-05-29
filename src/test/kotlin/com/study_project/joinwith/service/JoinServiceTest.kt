package com.study_project.joinwith.service


import com.study_project.joinwith.model.request.ChangePasswordRequest
import com.study_project.joinwith.model.request.DeleteUserRequest
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.Modifying
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class JoinServiceTest(
    private val joinwithRepository: JoinwithRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val joinService = JoinService(joinwithRepository, passwordEncoder)

    @AfterAll
    @Modifying(clearAutomatically = true)
    fun deleteTestData() {
        println(joinService.find())
        val testUserData = DeleteUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestChangedPw"
        }

        val result = joinService.deleteUser(testUserData)
        Assertions.assertEquals(true, result)

    }

    @Test
    @Order(1)
    @Modifying(clearAutomatically = true)
    fun saveTest() {
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = "JoinTestPw"
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }

        val result = joinService.save(userData)

        Assertions.assertEquals(true, result)
    }

    @Test
    @Order(2)
    fun overlapCheckTest() {

        val overlapId = joinService.overlapCheck("JoinTestID")
        val notOverlapId = joinService.overlapCheck("JoinTestID_not_overlap")

        Assertions.assertEquals(true, overlapId)
        Assertions.assertEquals(false, notOverlapId)
    }

    @Test
    @Order(3)
    @Modifying(clearAutomatically = true)
    fun changePasswordTest() {
        val userData = ChangePasswordRequest("JoinTestID", "JoinTestPw", "JoinTestChangedPw")
        val result = joinService.changePassword(userData)
        Assertions.assertEquals(true, result)

        val userDataNotCorrectPw = ChangePasswordRequest("JoinTestID", "JoinTestPw", "JoinTestChangedPw")
        val resultNotCorrectPw = joinService.changePassword(userDataNotCorrectPw)
        Assertions.assertEquals(false, resultNotCorrectPw)

        val userDataNotExistId = ChangePasswordRequest("NotExistJoinTestID", "NotExistJoinTestPw", "NotExistJoinTestChangedPw")
        val resultNotExistId = joinService.changePassword(userDataNotExistId)
        Assertions.assertEquals(false, resultNotExistId)


    }

    @Test
    @Order(4)
    fun validateUserTest() {
        val userWithChangedPw = ValidateUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestChangedPw"
        }

        val userWithNotChangedPw = ValidateUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestPw"
        }

        val resultWithChangedPw = joinService.validateUser(userWithChangedPw)
        println(resultWithChangedPw)
        val resultWithNotChangedPw = joinService.validateUser(userWithNotChangedPw)

        Assertions.assertEquals(true, resultWithChangedPw)
        Assertions.assertEquals(false, resultWithNotChangedPw)
    }

}