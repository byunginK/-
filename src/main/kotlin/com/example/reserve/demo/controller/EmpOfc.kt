package com.example.reserve.demo.controller

import com.example.reserve.demo.dto.EmpOfcUseDto
import com.example.reserve.demo.service.Reservation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "office seat", description = "사무실 좌석 예약 관련 서비스")
interface EmpOfc {
    val service: Reservation
        get() = object : Reservation {}

    @Operation(summary = "사무실 좌석 예약")
    @PostMapping("/office-seat")
    fun reserveOfficeSeat(@Parameter(description = "직원 id, 사무실 좌석, 예약 날짜 정보",
                                     required = true) @RequestBody empOfcUseDto: EmpOfcUseDto?): ResponseEntity<*> {
        return ResponseEntity.ok(service.reserveOfficeSeat(empOfcUseDto))
    }

    @Operation(summary = "사무실 좌석 예약 취소")
    @PatchMapping("/cancel/office-seat")
    fun cancelReserve(@Parameter(description = "직원 id, 취소할 사무실 좌석, 취소할 날짜 정보",
                                 required = true) @RequestBody empOfcUseDto: EmpOfcUseDto?): ResponseEntity<*> {
        return ResponseEntity.ok(service.cancelReserve(empOfcUseDto))
    }
}