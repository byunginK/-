package com.example.reserve.demo.controller

import com.example.reserve.demo.service.Reservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/api")
class EmpOfcController(@Autowired(required = false) delegate: Reservation?) : EmpOfc {

    override val service: Reservation

    init {
        service = delegate ?: object : Reservation {}
    }
}