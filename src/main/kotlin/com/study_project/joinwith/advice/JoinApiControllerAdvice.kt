package com.study_project.joinwith.advice

import com.study_project.joinwith.controller.api.join.JoinApiController
import com.study_project.joinwith.model.response.Error
import com.study_project.joinwith.model.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(basePackageClasses = [JoinApiController::class])
class JoinApiControllerAdvice {

    // 올바르지 않은 파라미터 (파라미터 수가 적은 경우)에 발생하는 Exception 처리
    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun missingServletRequestParameterException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        // ResponseEntity로 응답
        val errorResponse = ErrorResponse().apply{
            this.resultCode = "Fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.path = request.requestURI.toString()
        }

        // 에러코드 생성
        Error().apply {
            this.errorCode = "Missing Parameter"
            this.message = "올바르지 않은 파라미터입니다."
            errorResponse.message.add(this)
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}