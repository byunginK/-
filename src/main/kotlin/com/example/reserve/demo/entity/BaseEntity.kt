package com.example.reserve.demo.entity

import com.example.reserve.demo.enums.UseYn
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @Column(name = "regist_date_time", updatable = false)
    @CreatedDate
    var registDateTime: LocalDateTime? = null

    @Column(name = "modify_date_time")
    @LastModifiedDate
    var modifyDateTime: LocalDateTime? = null

}