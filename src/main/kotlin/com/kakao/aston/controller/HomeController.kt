package com.kakao.aston.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("")
class HomeController {
    @GetMapping("")
    @ResponseBody
    fun index(): String {
        return "Kotlin Api Page"
    }

    @GetMapping("/headers")
    fun headers(
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity(headers, HttpStatus.OK)
    }


    @GetMapping("/callback")
    fun callback(
        @Request headers: Map<String, String>
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity(headers, HttpStatus.OK)
    }



}