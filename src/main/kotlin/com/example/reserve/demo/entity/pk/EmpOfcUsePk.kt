package com.example.reserve.demo.entity.pk

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Embeddable
data class EmpOfcUsePk(@Column(name = "office_seat_id")
                       val officeSeatId: Long,
                       @Column(name = "reserve_seat_date")
                       val reserveSeatDate: LocalDate) : Serializable {

}