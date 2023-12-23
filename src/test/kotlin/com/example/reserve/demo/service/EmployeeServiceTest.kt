package com.example.reserve.demo.service

import com.example.reserve.demo.enums.ResultStatus
import com.example.reserve.demo.repository.queryDSL.EmployeeQuery
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private val employeeService: EmployeeService? = null

    /**
     * 직원 조회
     */
    @Test
    fun findEmployeeList() {
        //given
        val pageIndex = 1

        //when
        val response = employeeService?.findEmployeeList(pageIndex)

        //then
        assertEquals(ResultStatus.S, response?.result)
        assertEquals(20, response?.dataList?.size)
        println(response?.dataList)
    }
}