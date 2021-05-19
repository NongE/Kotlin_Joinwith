package com.study_project.joinwith.annotation

import com.study_project.joinwith.validator.DateCheckValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass


@Constraint(validatedBy = [DateCheckValidator::class])
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class DateCheck(
    val pattern: String = "yyyy-MM-dd",
    val message: String = "올바르지 않는 날짜 형식입니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)