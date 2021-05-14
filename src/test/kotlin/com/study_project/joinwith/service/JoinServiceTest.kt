package com.study_project.joinwith.service

import com.study_project.joinwith.config.PasswordEncoderConfig
import com.study_project.joinwith.config.WebSecurityConfig
import com.study_project.joinwith.database.Join
import com.study_project.joinwith.database.convertJoin
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.repository.JoinwithRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

        val result = joinwithRepository.save(Join().convertJoin(userData))
        val result2 = joinService.save(userData)

        Assertions.assertEquals("JoinTestID", result.userId)
        Assertions.assertEquals(true, result2)
    }

}