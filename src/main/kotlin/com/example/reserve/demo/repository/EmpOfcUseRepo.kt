package com.example.reserve.demo.repository

import com.example.reserve.demo.entity.EmpOfcUse
import com.example.reserve.demo.entity.pk.EmpOfcUsePk
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface EmpOfcUseRepo : JpaRepository<EmpOfcUse?, EmpOfcUsePk?> {
    override fun findAll(): List<EmpOfcUse?>
    fun findByEmpOfcUsePk_OfficeSeatIdAndEmpOfcUsePk_ReserveSeatDate(@Param(value = "office_seat_id") officeSeatId: Long,
                                                                     @Param(value = "reserve_seat_date") reserveSeatDate: LocalDate): List<EmpOfcUse?>
}