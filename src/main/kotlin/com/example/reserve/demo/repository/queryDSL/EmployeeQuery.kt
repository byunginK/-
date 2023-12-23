package com.example.reserve.demo.repository.queryDSL

import com.example.reserve.demo.dto.EmployeeDto
import com.example.reserve.demo.dto.QEmployeeDto
import com.example.reserve.demo.entity.QEmpOfcUse
import com.example.reserve.demo.entity.QEmployee
import com.example.reserve.demo.entity.QOfficeSeat
import com.example.reserve.demo.enums.UseYn
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class EmployeeQuery(private val queryFactory: JPAQueryFactory) {
    fun findEmployeeAndSeat(pageable: Pageable): Page<EmployeeDto> {
        val employeeDtoList = queryFactory.select(QEmployeeDto(QEmployee.employee.employeeId,
                                                               QEmployee.employee.name,
                                                               QEmployee.employee.vacationYn,
                                                               QOfficeSeat.officeSeat.officeSeatNumber))
                .from(QEmployee.employee).leftJoin(QEmpOfcUse.empOfcUse)
                .on(QEmployee.employee.employeeId.eq(QEmpOfcUse.empOfcUse.employeeId),
                    QEmpOfcUse.empOfcUse.useYn.eq(UseYn.Y.value)).leftJoin(QOfficeSeat.officeSeat)
                .on(QEmpOfcUse.empOfcUse.empOfcUsePk.officeSeatId.eq(QOfficeSeat.officeSeat.officeSeatId))
                .where(QEmployee.employee.useYn.eq(UseYn.Y.value)).offset(pageable.offset)
                .limit(pageable.pageSize.toLong()).fetch()
        val countQuery =
            queryFactory.select(QEmployee.employee.employeeId.count()).from(QEmployee.employee)
                    .leftJoin(QEmpOfcUse.empOfcUse)
                    .on(QEmployee.employee.employeeId.eq(QEmpOfcUse.empOfcUse.employeeId),
                        QEmpOfcUse.empOfcUse.useYn.eq(UseYn.Y.value))
                    .leftJoin(QOfficeSeat.officeSeat)
                    .on(QEmpOfcUse.empOfcUse.empOfcUsePk.officeSeatId.eq(QOfficeSeat.officeSeat.officeSeatId))
                    .where(QEmployee.employee.useYn.eq(UseYn.Y.value))
        return if (employeeDtoList == null) PageableExecutionUtils.getPage(ArrayList<EmployeeDto>(),
                                                                           pageable) { countQuery.fetchFirst() } else PageableExecutionUtils.getPage(
            employeeDtoList,
            pageable) { countQuery.fetchFirst() }
    }

}