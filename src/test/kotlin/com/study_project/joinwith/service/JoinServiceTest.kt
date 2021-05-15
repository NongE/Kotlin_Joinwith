package com.study_project.joinwith.service

import com.study_project.joinwith.config.PasswordEncoderConfig
import com.study_project.joinwith.config.WebSecurityConfig
import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.OverlapCheckRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.Modifying
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class JoinServiceTest(
    private val joinwithRepository: JoinwithRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    val joinService = JoinService(joinwithRepository, passwordEncoder)

    @AfterEach
    fun deleteTestData() {


    }

    @Modifying(clearAutomatically = true)
    @Test
    fun saveTest() {
        val userData = JoinRequest().apply {
            this.user_id = "JoinTestID"
            this.pw = passwordEncoder.encode("JoinTestPw")
            this.userName = "JoinTestName"
            this.address = "JoinTestAddress"
            this.birth = "JoinTestBirth"
            this.email = "JoinTestEmail"
            this.phoneNumber = "JoinTestPhoneNumber"
        }

        val result = joinService.save(userData)

        Assertions.assertEquals(true, result)
    }


    @Modifying(clearAutomatically = true)
    @Test
    fun overlapCheckTest() {

        val overlapId = joinService.overlapCheck("JoinTestID")
        val notOverlapId = joinService.overlapCheck("JoinTestID_not_overlap")

        Assertions.assertEquals(true, overlapId)
        Assertions.assertEquals(false, notOverlapId)
    }


}