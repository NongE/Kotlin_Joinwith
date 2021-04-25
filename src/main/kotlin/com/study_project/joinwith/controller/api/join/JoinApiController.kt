package com.study_project.joinwith.controller.api.join

import com.study_project.joinwith.model.Join
import com.study_project.joinwith.repository.JoinwithRepository
import com.study_project.joinwith.service.JoinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(path = ["/api/join"])
class JoinApiController(
    var joinService:JoinService
) {

    // 기본적으로 주입을 하기 위해서는 주입하기 위한 객체가 bean으로 등록되어 있어야함
    // service, repository등 다양한 방법이 존재
    // autowired나 생성자로 받는 방법의 차이는 스프링에서 reflection으로 강제 주입한 방식임
    // 다른 필드에서 사용 불가능

    //Autowired
    //private lateinit var joinService:JoinService


    @PostMapping(path = [""])
    fun join(
        @RequestBody
        join: Join
    ): ResponseEntity<Any?>{
        println(join.id)
        return ResponseEntity.ok().body(joinService.save(join))
    }
    @GetMapping(path = [""])
    fun find(): MutableIterable<Join> {
        println(joinService.find())
        return joinService.find()
    }

}