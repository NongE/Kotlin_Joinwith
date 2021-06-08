package com.study_project.joinwith.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2



@Configuration
@EnableSwagger2
// Swagger 관련 Config
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .apiInfo(setApiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.study_project.joinwith"))
            .build()
    }

    // api 에 대한 설명을 작성하는 곳
    private fun setApiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("Joinwith API Documentation") // 제목
            .version("1.1") // 버전
            .description("Kotlin과 Spring Boot를 활용한 회원가입(관리) API") // 요약
            .build()
    }

}