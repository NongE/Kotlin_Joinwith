package com.study_project.joinwith.controller.api.join

import com.study_project.joinwith.database.Join
import com.study_project.joinwith.model.request.ChangePasswordRequest
import com.study_project.joinwith.model.request.DeleteUserRequest
import com.study_project.joinwith.model.request.JoinRequest
import com.study_project.joinwith.model.request.ValidateUserRequest
import com.study_project.joinwith.model.response.*
import com.study_project.joinwith.service.JoinService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


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
        return joinService.find()
    }

    @PostMapping(path = ["/overlap_check"])
    @ApiOperation(value = "ID 중복 검사 API",
        notes = "사용자가 사용하려는 아이디가 현재 DB에 있는지 중복 검사하는 API // false = 사용 가능한 ID, true = 중복된 ID")
    fun overlapCheck(
        @ApiParam(value = "사용하고자 하는 ID", example = "helloWorld")
        @RequestParam userId: String,
        request: HttpServletRequest,
    ): ResponseEntity<OverlapCheckResponse> {
        println(joinService.overlapCheck(userId))
        if (joinService.overlapCheck(userId)) {
            OverlapCheckResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Overlap Check Fail!"
                this.httpStatus = HttpStatus.OK.toString()
                this.message = "${userId}는 사용할 수 없는 아이디입니다."
                return ResponseEntity.status(HttpStatus.OK).body(this)
            }
        } else {
            OverlapCheckResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Overlap Check Success!"
                this.httpStatus = HttpStatus.BAD_REQUEST.toString()
                this.message = "${userId}는 사용 가능한 아이디입니다."
                return ResponseEntity.status(HttpStatus.OK).body(this)
            }
        }

    }

    @PostMapping(path = [""])
    @ApiOperation(value = "회원가입 API", notes = "사용자의 정보를 전달받아 이를 DB에 저장하는 API")
    fun join(
        @Valid
        @RequestBody
        joinRequest: JoinRequest,
        bindingResult: BindingResult,
        request: HttpServletRequest,

    ): ResponseEntity<Any> {

        if (bindingResult.hasErrors()) {
            val errorResponse = ErrorResponse()

            for (errorElement in bindingResult.allErrors) {
                Error().apply {
                    this.errorCode = errorElement.code
                    this.message = errorElement.defaultMessage
                    errorResponse.message.add(this)
                }
            }
            return ResponseEntity.badRequest().body(errorResponse)
        } else {
            if (joinService.save(joinRequest)){
                JoinResponse().apply {
                    this.path = request.requestURI.toString()
                    this.resultCode = "Join Success!"
                    this.httpStatus = HttpStatus.OK.toString()
                    this.id = joinRequest.user_id
                    this.message = "${this.id}님 가입 완료"
                    return ResponseEntity.status(HttpStatus.OK).body(this)
                }
            }else{
                JoinResponse().apply {
                    this.path = request.requestURI.toString()
                    this.resultCode = "Join Fail!"
                    this.httpStatus = HttpStatus.BAD_REQUEST.toString()
                    this.id = joinRequest.user_id
                    this.message = "${this.id} 가입 실패"
                    return ResponseEntity.status(HttpStatus.OK).body(this)
                }
            }
        }


    }

    @PostMapping(path = ["/validate_user"])
    @ApiOperation(value = "사용자 검증 API",
        notes = "아이디와 비밀번호를 활용하여 현재 사용자가 유효한 사용자인지 검증하는 API // false = 검증 실패, true = 검증 완료")
    fun validateUser(
        @RequestBody validateUserRequest: ValidateUserRequest,
        request: HttpServletRequest,
    ): ResponseEntity<ValidateUserResponse> {
        if (joinService.validateUser(validateUserRequest)) {
            ValidateUserResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Validate Success!"
                this.httpStatus = HttpStatus.OK.toString()
                this.message = "아이디, 비밀번호 검증 완료"
                return ResponseEntity.status(HttpStatus.OK).body(this)
            }
        } else {
            ValidateUserResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Validate Fail!"
                this.httpStatus = HttpStatus.BAD_REQUEST.toString()
                this.message = "아이디, 비밀번호 검증 실패"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this)
            }
        }
    }

    @PatchMapping(path = ["/change_password"])
    @ApiOperation(value = "사용자 비밀번호 변경 API",
        notes = "사용자의 비밀번호를 변경하는 API // false = 변경 실패, true = 변경 성공")
    fun changePassword(
        @RequestBody changePasswordRequest: ChangePasswordRequest,
        request: HttpServletRequest,
    ): ResponseEntity<ChangePasswordResponse> {

        if (joinService.changePassword(changePasswordRequest)) {
            ChangePasswordResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Change Password Success!"
                this.httpStatus = HttpStatus.OK.toString()
                this.message = "비밀번호 변경 완료"
                return ResponseEntity.status(HttpStatus.OK).body(this)
            }
        } else {
            ChangePasswordResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Change Password Fail!"
                this.httpStatus = HttpStatus.BAD_REQUEST.toString()
                this.message = "비밀번호 변경 실패"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this)
            }
        }

    }

    @DeleteMapping(path = ["/delete_password"])
    @ApiOperation(value = "사용자 삭제 API",
        notes = "사용자를 삭제하는 API")
    fun deleteUser(
        @RequestBody deleteUserRequest: DeleteUserRequest,
        request: HttpServletRequest,
    ): ResponseEntity<DeleteUserResponse> {

        if (joinService.deleteUser(deleteUserRequest)) {
            DeleteUserResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Delete User Success!"
                this.httpStatus = HttpStatus.OK.toString()
                this.message = "회원정보 삭제 완료"
                return ResponseEntity.status(HttpStatus.OK).body(this)
            }
        } else {
            DeleteUserResponse().apply {
                this.path = request.requestURI.toString()
                this.resultCode = "Delete User Fail!"
                this.httpStatus = HttpStatus.BAD_REQUEST.toString()
                this.message = "회원정보 삭제 실패"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this)
            }
        }


    }

}