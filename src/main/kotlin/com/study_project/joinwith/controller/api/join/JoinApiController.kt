package com.study_project.joinwith.controller.api.join

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.ChangePasswordRequest
import com.study_project.joinwith.model.JoinRequest
import com.study_project.joinwith.model.OverlapCheckRequest
import com.study_project.joinwith.model.ValidateUserRequest
import com.study_project.joinwith.service.JoinService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["/api/join"])
class JoinApiController(
    var joinService: JoinService,
) {

    // 기본적으로 주입을 하기 위해서는 주입하기 위한 객체가 bean으로 등록되어 있어야함
    // service, repository등 다양한 방법이 존재
    // autowired나 생성자로 받는 방법의 차이는 스프링에서 reflection으로 강제 주입한 방식임
    // 다른 필드에서 사용 불가능

    //Autowired
    //private lateinit var joinService:JoinService

    @GetMapping(path = [""])
    @ApiOperation(value = "전체 회원 목록을 불러오는 API", notes = "임시로 회원 목록을 확인하기 위해 전체 회원 목록을 불러오는 API")
    fun find(): MutableIterable<Join> {
        println(joinService.find())
        return joinService.find()
    }

    @PostMapping(path = ["/overlap_check"])
    @ApiOperation(value = "ID 중복 검사 API",
        notes = "사용자가 사용하려는 아이디가 현재 DB에 있는지 중복 검사하는 API // false = 사용 가능한 ID, true = 중복된 ID")
    fun overlapCheck(
        @ApiParam(value = "사용하고자 하는 ID", example = "helloWorld") @RequestParam userId:String
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(joinService.overlapCheck(userId))
    }

    @PostMapping(path = [""])
    @ApiOperation(value = "회원가입 API", notes = "사용자의 정보를 전달받아 이를 DB에 저장하는 API")
    fun join(
         @RequestBody joinRequest: JoinRequest,
    ): ResponseEntity<Any?> {
        println(joinRequest)
        return ResponseEntity.ok().body(joinService.save(joinRequest))
    }

    @PostMapping(path = ["/validate_user"])
    @ApiOperation(value = "사용자 검증 API",
        notes = "아이디와 비밀번호를 활용하여 현재 사용자가 유효한 사용자인지 검증하는 API // false = 검증 실패, true = 검증 완료")
    fun validateUser(
        @RequestBody validateUserRequest: ValidateUserRequest
    ): Boolean {
        return joinService.validateUser(validateUserRequest)
    }

    @PatchMapping(path = ["/change_password"])
    @ApiOperation(value = "사용자 비밀번호 변경 API",
        notes = "사용자의 비밀번호를 변경하는 API // false = 변경 실패, true = 변경 성공")
    fun changePassword(
        @RequestBody changePasswordRequest: ChangePasswordRequest
    ): Boolean {
        return joinService.changePassword(changePasswordRequest)
    }

    @DeleteMapping(path = ["/change_password"])
    @ApiOperation(value = "사용자 삭제 API",
        notes = "사용자를 삭제하는 API // false = 삭제 실패, true = 삭제 성공")
    fun deleteUser(
        @RequestBody validateUserRequest: ValidateUserRequest
    ): Boolean {
        return joinService.deleteUser(validateUserRequest)
    }

}