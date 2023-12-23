package com.example.reserve.demo.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate

data class EmpOfcUseDto @QueryProjection constructor(
    var employeeId: Long,
    var officeSeatId: Long,
    var reserveSeatDate: LocalDate
) {

}