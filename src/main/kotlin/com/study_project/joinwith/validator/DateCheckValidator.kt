package com.study_project.joinwith.validator

import com.study_project.joinwith.annotation.DateCheck
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

// 입력받은 내용이 날짜형식에 맞는지 확인하는 클래스
class DateCheckValidator : ConstraintValidator<DateCheck, String> {

    private var pattern: String? = null
    override fun initialize(constraintAnnotation: DateCheck?) {
        this.pattern = constraintAnnotation?.pattern
        super.initialize(constraintAnnotation)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return try {
            LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern))
            true
        } catch (e: Exception) {
            false
        }
    }

}