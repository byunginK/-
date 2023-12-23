package com.example.reserve.demo.service

import com.example.reserve.demo.dto.EmployeeDto
import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.enums.ErrorCode
import com.example.reserve.demo.enums.ResultStatus

interface Employee {
    fun findEmployeeList(pageIndex: Int): ResponseDto<EmployeeDto>{
        return ResponseDto(
            result = ResultStatus.E,
            resultMessage = "NOT IMPLEMENT",
            code = 500,
            errorMessage = ErrorCode.INTERNAL_SERVER_ERROR.value
        )
    }
}