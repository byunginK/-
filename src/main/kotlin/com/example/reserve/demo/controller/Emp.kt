package com.example.reserve.demo.controller

import com.example.reserve.demo.service.Employee
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "Employee", description = "직원 조회 관련 서비스")
interface Emp {
    val service: Employee
        get() = object : Employee {}

    @Operation(summary = "직원 조회")
    @GetMapping("/all/{pageIndex}")
    fun getEmployeeList(@Parameter(description = "페이지 인텍스",
                                   required = true) @PathVariable pageIndex: Int): ResponseEntity<*> {
        return ResponseEntity.ok(service.findEmployeeList(pageIndex))
    }
}