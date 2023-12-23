package com.example.reserve.demo.service

import com.example.reserve.demo.dto.EmpOfcUseDto
import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.entity.EmpOfcUse
import com.example.reserve.demo.entity.pk.EmpOfcUsePk
import com.example.reserve.demo.enums.ErrorCode
import com.example.reserve.demo.enums.ResultStatus
import com.example.reserve.demo.enums.UseYn
import com.example.reserve.demo.lock.UserLevelLockFinal
import com.example.reserve.demo.repository.EmpOfcUseRepo
import com.example.reserve.demo.repository.queryDSL.EmpOfcUseQuery
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class ReservationService(private val empOfcUseRepo: EmpOfcUseRepo,
                         private val empOfcUseQuery: EmpOfcUseQuery,
                         private val userLevelLockFinal: UserLevelLockFinal,
                         private val officeSeatService: OfficeSeatService) : Reservation {
    private val RESERVE_AVAILABLE_COUNT = 1
    override fun reserveOfficeSeat(empOfcUseDto: EmpOfcUseDto?): ResponseDto<EmpOfcUseDto> {
        return try {
            val employeeId = empOfcUseDto!!.employeeId.toLong()
            val officeSeatId = empOfcUseDto.officeSeatId.toLong()
            val nowDate = LocalDate.now()

            //사무실 좌석 validation
            val officeSeatDto = officeSeatService.findOfficeSeat(officeSeatId)

            //하루 1번 이상 예약 X, 한사람당 하루에 1개 예약 가능
            val reservedList = empOfcUseQuery.getEmpOfcUseList(employeeId, nowDate)

            checkAvailableReserve(reservedList, officeSeatDto?.officeSeatId!!, employeeId, nowDate)
        } catch (ex: Exception) {
            log.error("", ex)
            throw ex
        }
    }

    private fun checkAvailableReserve(reservedList: List<EmpOfcUse>,
                                      officeSeatId: Long,
                                      employeeId: Long,
                                      nowDate: LocalDate): ResponseDto<EmpOfcUseDto> {
        val reserveCount = reservedList.count { empOfcUse ->
            empOfcUse.useYn == UseYn.Y.value || empOfcUse.useYn == UseYn.N.value && empOfcUse.empOfcUsePk.officeSeatId == officeSeatId
        }
        val officeSeatCount = empOfcUseQuery.getOfficeSeatCount(officeSeatId, nowDate)

        //1번 이상일 경우 에러 응답
        return if (reserveCount >= RESERVE_AVAILABLE_COUNT || officeSeatCount > 0) {
            ResponseDto(result = ResultStatus.E,
                        resultMessage = ResultStatus.E.value,
                        code = 400,
                        errorMessage = ErrorCode.AVAILABLE_RESERVE_OVER_TIME.value)
        } else {
            val empOfcUsePk = EmpOfcUsePk(officeSeatId, nowDate)

            val empOfcUse = EmpOfcUse(empOfcUsePk = empOfcUsePk, employeeId = employeeId)
            userLevelLockFinal.executeWithLock(officeSeatId.toString(), 300) {
                empOfcUseRepo.save(empOfcUse)
            }


            ResponseDto(result = ResultStatus.S,
                        resultMessage = ResultStatus.S.value,
                        code = 201,
                        data = empOfcUse.toDto())
        }
    }

    override fun cancelReserve(empOfcUseDto: EmpOfcUseDto?): ResponseDto<EmpOfcUseDto> {
        return try {
            val employeeId = empOfcUseDto!!.employeeId.toLong()
            val date = empOfcUseDto.reserveSeatDate
            val officeSeatId = empOfcUseDto.officeSeatId.toLong()
            val empOfcUse =
                empOfcUseQuery.findEmpOfcUse(employeeId, officeSeatId, date, UseYn.Y.value)

            empOfcUse?.updateUseYn(UseYn.N)

            ResponseDto(result = ResultStatus.S,
                        resultMessage = ResultStatus.S.value,
                        code = 200,
                        data = empOfcUse?.toDto())
        } catch (ex: Exception) {
            log.error("", ex)
            throw ex
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(ReservationService::class.java)
    }
}