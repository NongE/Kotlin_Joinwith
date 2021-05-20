package com.study_project.joinwith.advice

import com.study_project.joinwith.controller.api.join.JoinApiController
import com.study_project.joinwith.model.response.Error
import com.study_project.joinwith.model.response.ErrorResponse
import com.study_project.joinwith.service.JoinService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(basePackageClasses = [JoinApiController::class])
class JoinApiControllerAdvice {

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun missingServletRequestParameterException(e: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse().apply{
            this.resultCode = "Fail"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            // this.message = "파라미터가 부족합니다."
            this.path = request.requestURI.toString()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}