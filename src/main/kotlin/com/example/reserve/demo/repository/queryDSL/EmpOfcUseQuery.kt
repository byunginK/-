package com.example.reserve.demo.repository.queryDSL

import com.example.reserve.demo.entity.EmpOfcUse
import com.example.reserve.demo.entity.QEmpOfcUse
import com.example.reserve.demo.enums.UseYn
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import java.time.LocalDate

@Repository
class EmpOfcUseQuery(private val queryFactory: JPAQueryFactory) {
    fun getEmpOfcUseList(employeeId: Long?, reserveDate: LocalDate?): List<EmpOfcUse> {
        return queryFactory.selectFrom(QEmpOfcUse.empOfcUse)
                .where(eqEmpIdAndDate(employeeId, reserveDate)).fetch() ?: emptyList()
    }

    fun getOfficeSeatCount(officeSeatId: Long?, reserveDate: LocalDate?): Long {
        return queryFactory.select(QEmpOfcUse.empOfcUse.empOfcUsePk.officeSeatId.count())
                .from(QEmpOfcUse.empOfcUse)
                .where(eqPk(officeSeatId, reserveDate), eqUseYn(UseYn.Y.value)).fetchFirst()
    }

    fun findEmpOfcUse(employeeId: Long?,
                      officeSeatId: Long?,
                      reserveDate: LocalDate?,
                      useYn: String): EmpOfcUse? {
        return queryFactory.selectFrom(QEmpOfcUse.empOfcUse)
                .where(eqEmpOfcUsePk(employeeId, officeSeatId, reserveDate), eqUseYn(useYn))
                .fetchFirst() ?: throw Exception("No Reserved history")
    }

    private fun eqEmpIdAndDate(employeeId: Long?, reserveDate: LocalDate?): BooleanExpression? {
        return if (employeeId == null || reserveDate == null) {
            null
        } else {
            QEmpOfcUse.empOfcUse.employeeId.eq(employeeId)
                    .and(QEmpOfcUse.empOfcUse.empOfcUsePk.reserveSeatDate.eq(reserveDate))
        }
    }

    private fun eqEmpOfcUsePk(employeeId: Long?,
                              officeSeatId: Long?,
                              reserveDate: LocalDate?): BooleanExpression? {
        return if (employeeId == null || officeSeatId == null || reserveDate == null) {
            null
        } else {
            QEmpOfcUse.empOfcUse.employeeId.eq(employeeId)
                    .and(QEmpOfcUse.empOfcUse.empOfcUsePk.officeSeatId.eq(officeSeatId))
                    .and(QEmpOfcUse.empOfcUse.empOfcUsePk.reserveSeatDate.eq(reserveDate))
        }
    }

    private fun eqPk(officeSeatId: Long?, reserveDate: LocalDate?): BooleanExpression? {
        return if (officeSeatId == null || reserveDate == null) {
            null
        } else {
            QEmpOfcUse.empOfcUse.empOfcUsePk.officeSeatId.eq(officeSeatId)
                    .and(QEmpOfcUse.empOfcUse.empOfcUsePk.reserveSeatDate.eq(reserveDate))
        }
    }

    private fun eqUseYn(useYn: String): BooleanExpression? {
        return if (StringUtils.hasText(useYn)) QEmpOfcUse.empOfcUse.useYn.eq(useYn) else null
    }
}