package com.example.reserve.demo.entity

import com.example.reserve.demo.enums.UseYn
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "employee")
class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    val employeeId: Long = 0L,

    @Column(name = "name")
    var name: String?,

    @Column(name = "vacation_yn")
    var vacationYn: String?,

    @Column(name = "use_yn")
    var useYn: String = "Y"
) : BaseEntity() {

    fun updateUseYn(useYn: UseYn) {
        this.useYn = useYn.value
    }


}