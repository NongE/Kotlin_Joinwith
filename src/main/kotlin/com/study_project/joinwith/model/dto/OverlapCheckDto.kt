package com.study_project.joinwith.model.dto

// JPA를 위한 인터페이스
interface OverlapCheckDto {
    fun getId():Int
    fun getUserId(): String
    fun getPw(): String
}