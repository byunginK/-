package com.example.reserve.demo.setup

import com.example.reserve.demo.entity.Employee
import com.example.reserve.demo.entity.OfficeSeat
import com.example.reserve.demo.enums.UseYn
import com.example.reserve.demo.repository.EmployeeRepo
import com.example.reserve.demo.repository.OfficeSeatRepo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreateDummyData {

    @Autowired
    private val employeeRepo: EmployeeRepo? = null

    @Autowired
    private val officeSeatRepo: OfficeSeatRepo? = null

    @Test
    fun createEmployee() {
        val employeeList = ArrayList<Employee>();
        for (i in 1..150) {
            val name = "emp$i";
            var vacationYn = "N";
            if (i % 15 == 0) {
                vacationYn = "Y";
            }
            val employee = Employee(name = name, vacationYn = vacationYn, useYn = UseYn.Y.value)
            employeeList.add(employee);
        }
        employeeRepo?.saveAllAndFlush(employeeList);
    }

    @Test
    fun createOfficeSeat() {
        val officeSeatList = ArrayList<OfficeSeat>();
        for (i in 1 .. 100) {
            val name = i.toString()
            val officeSeat = OfficeSeat(officeSeatNumber = name,
                                        officeSeatLocation = "테스트 장소",
                                        useYn = UseYn.Y.value)
            officeSeatList.add(officeSeat);
        }
        officeSeatRepo?.saveAllAndFlush(officeSeatList);
    }

}