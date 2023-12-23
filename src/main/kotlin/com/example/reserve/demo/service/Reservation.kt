package com.example.reserve.demo.service

import com.example.reserve.demo.dto.EmpOfcUseDto
import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.enums.ErrorCode
import com.example.reserve.demo.enums.ResultStatus
import java.time.LocalDate

interface Reservation {
    fun reserveOfficeSeat(empOfcUseDto: EmpOfcUseDto?): ResponseDto<EmpOfcUseDto> {
        return ResponseDto(
            result = ResultStatus.E,
            resultMessage = "NOT IMPLEMENT",
            code = 500,
            errorMessage = ErrorCode.INTERNAL_SERVER_ERROR.value
        )
    }

    fun cancelReserve(empOfcUseDto: EmpOfcUseDto?): ResponseDto<EmpOfcUseDto> {
        return ResponseDto(
            result = ResultStatus.E,
            resultMessage = "NOT IMPLEMENT",
            code = 500,
            errorMessage = ErrorCode.INTERNAL_SERVER_ERROR.value
        )
    }
}