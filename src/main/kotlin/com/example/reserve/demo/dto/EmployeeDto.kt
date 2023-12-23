package com.example.reserve.demo.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.querydsl.core.annotations.QueryProjection

@JsonInclude(JsonInclude.Include.NON_NULL)
data class EmployeeDto constructor(val employeeId: Long,
                                   var name: String,
                                   var workType: String,
                                   var vacationYn: String?,
                                   var officeSeatNumber: String?) {

    @QueryProjection
    constructor(employeeId: Long,
                name: String,
                vacationYn: String?,
                officeSeatNumber: String?) : this(employeeId,
                                                  name,
                                                  "",
                                                  vacationYn,
                                                  officeSeatNumber)

}