package com.example.reserve.demo.repository

import com.example.reserve.demo.entity.OfficeSeat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface OfficeSeatRepo : JpaRepository<OfficeSeat?, Long?> {
    fun findByOfficeSeatIdAndUseYn(
            @Param(value = "office_seat_id") officeSeatId: Long,
            @Param(value = "use_yn") useYn: String?
    ): OfficeSeat?
}