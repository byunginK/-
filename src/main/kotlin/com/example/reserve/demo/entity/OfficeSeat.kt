package com.example.reserve.demo.entity

import com.example.reserve.demo.dto.OfficeSeatDto
import com.example.reserve.demo.enums.UseYn
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "office_seat")
class OfficeSeat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val officeSeatId: Long = 0L,

    @Column(name = "office_seat_number")
    var officeSeatNumber: String,

    @Column(name = "office_seat_location")
    var officeSeatLocation: String,

    @Column(name = "use_yn")
    var useYn: String = "Y"
) : BaseEntity() {

    fun toDto(): OfficeSeatDto {
        val officeSeatId = this.officeSeatId
        val officeSeatNumber = this.officeSeatNumber
        val officeSeatLocation = this.officeSeatLocation
        return OfficeSeatDto(officeSeatId, officeSeatNumber, officeSeatLocation)
    }

    fun updateUseYn(useYn: UseYn) {
        this.useYn = useYn.value
    }
}