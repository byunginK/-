package com.example.reserve.demo.service

import com.example.reserve.demo.dto.EmployeeDto
import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.enums.ResultStatus
import com.example.reserve.demo.enums.UseYn
import com.example.reserve.demo.repository.queryDSL.EmployeeQuery
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils

@Service
@Transactional
class EmployeeService(private val employeeQuery: EmployeeQuery) : Employee {
    private val PAGE_SIZE = 20

    @Transactional(readOnly = true)
    override fun findEmployeeList(pageIndex: Int): ResponseDto<EmployeeDto> {
        return try {
            //20명 씩 페이징 처리
            val pageable: Pageable = PageRequest.of((pageIndex - 1) ?: 0, PAGE_SIZE)
            val page = employeeQuery.findEmployeeAndSeat(pageable)
            val employeeDtoList = page.content
            val totalItem = page.totalElements
            val totalPage = page.totalPages

            //근무 형태 분류
            val result = employeeDtoList.map { employeeDto ->
                EmployeeDto(employeeId = employeeDto.employeeId,
                            name = employeeDto.name,
                            workType = getWorkType(employeeDto.vacationYn, employeeDto.officeSeatNumber),
                            vacationYn = null,
                            officeSeatNumber = employeeDto.officeSeatNumber)
            }.toList()

            ResponseDto(result = ResultStatus.S,
                        resultMessage = ResultStatus.S.value,
                        code = 200,
                        itemTotalCount = totalItem,
                        pageTotalCount = totalPage,
                        dataList = result)
        } catch (ex: Exception) {
            log.error("", ex)
            throw ex
        }
    }
    fun getWorkType(vacationYn: String?, officeSeatNumber: String?) : String {
        return if (vacationYn == UseYn.Y.value) {
            "휴가"
        } else if (StringUtils.hasText(officeSeatNumber)) {
            "오피스 출근"
        } else {
            "재택"
        }
    }
    companion object {
        private val log = LoggerFactory.getLogger(EmployeeService::class.java)
    }
}