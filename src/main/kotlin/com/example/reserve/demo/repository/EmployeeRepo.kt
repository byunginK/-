package com.example.reserve.demo.repository

import com.example.reserve.demo.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface EmployeeRepo : JpaRepository<Employee?, Long?> {
    fun findByEmployeeIdAndUseYn(
            @Param(value = "employee_id") employeeId: Long, @Param(value = "use_yn") useYn: String?
    ): Optional<Employee?>?
}