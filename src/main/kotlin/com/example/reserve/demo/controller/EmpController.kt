package com.example.reserve.demo.controller

import com.example.reserve.demo.service.Employee
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/employee")
class EmpController(@Autowired(required = false) delegate: Employee?) : Emp {
    override val service: Employee

    init {
        service = delegate ?: object : Employee {}
    }
}