package com.study_project.joinwith.service


import com.study_project.joinwith.model.request.ChangePasswordRequest
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
class JoinServiceTest(
    private val joinwithRepository: JoinwithRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val joinService = JoinService(joinwithRepository, passwordEncoder)

    @AfterAll
    @Modifying(clearAutomatically = true)
    fun deleteTestData() {
        println(joinService.find())
        val testUserData = ValidateUserRequest().apply {
            this.userId = "JoinTestID"
            this.pw = "JoinTestChangedPw"
        }

        val result = joinService.deleteUser(testUserData)
        Assertions.assertEquals(true, result)

    }

    @Test
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
    fun overlapCheckTest() {

        val overlapId = joinService.overlapCheck("JoinTestID")
        val notOverlapId = joinService.overlapCheck("JoinTestID_not_overlap")

        Assertions.assertEquals(true, overlapId)
        Assertions.assertEquals(false, notOverlapId)
    }

    @Test
    @Modifying(clearAutomatically = true)
    fun changePasswordTest() {
        val userData = ChangePasswordRequest("JoinTestID", "JoinTestPw", "JoinTestChangedPw")

        val result = joinService.changePassword(userData)

        Assertions.assertEquals(true, result)

    }


}