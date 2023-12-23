package com.example.reserve.demo.entity

import com.example.reserve.demo.dto.EmpOfcUseDto
import com.example.reserve.demo.entity.pk.EmpOfcUsePk
import com.example.reserve.demo.enums.UseYn
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "emp_ofc_use")
class EmpOfcUse(@EmbeddedId val empOfcUsePk: EmpOfcUsePk,

                @Column(name = "employee_id") var employeeId: Long,

                @Column(name = "use_yn") var useYn: String = "Y") : BaseEntity() {
    fun updateUseYn(useYn: UseYn) {
        this.useYn = useYn.value
    }

    fun toDto(): EmpOfcUseDto {
        val employeeId = this.employeeId
        val officeSeatId = this.empOfcUsePk.officeSeatId
        val reserveSeatDate = this.empOfcUsePk.reserveSeatDate
        return EmpOfcUseDto(employeeId = employeeId,
                            officeSeatId = officeSeatId,
                            reserveSeatDate = reserveSeatDate)
    }

}