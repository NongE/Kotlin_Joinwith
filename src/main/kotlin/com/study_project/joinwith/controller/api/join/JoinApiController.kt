package com.study_project.joinwith.controller.api.join

import com.study_project.joinwith.model.Join
import com.study_project.joinwith.repository.JoinwithRepository
import com.study_project.joinwith.service.JoinService
import io.swagger.annotations.ApiOperation
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

    @GetMapping(path = [""])
    @ApiOperation(value = "전체 회원 목록을 불러오는 API", notes = "임시로 회원 목록을 확인하기 위해 전체 회원 목록을 불러오는 API")
    fun find(): MutableIterable<Join> {
        println(joinService.find())
        return joinService.find()
    }

    @PostMapping(path = [""])
    @ApiOperation(value = "회원가입 API", notes = "사용자의 정보를 전달받아 이를 DB에 저장하는 API")
    fun join(
        @RequestBody join: Join
    ): ResponseEntity<Any?>{
        return ResponseEntity.ok().body(joinService.save(join))
    }

    @PostMapping(path = ["/id_overlap"])
    @ApiOperation(value = "ID 중복 검사 API", notes = "사용자가 사용하려는 아이디가 현재 DB에 있는지 중복 검사하는 API, false = 사용 가능한 ID, true = 중복된 ID")
    fun idOverlap(
        @RequestParam id:String
    ): Boolean {
        return joinService.idOverlap(id)
    }

}