package com.example.reserve.demo.service

import com.example.reserve.demo.dto.EmpOfcUseDto
import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.entity.EmpOfcUse
import com.example.reserve.demo.enums.ResultStatus
import com.example.reserve.demo.enums.UseYn
import com.example.reserve.demo.repository.EmpOfcUseRepo
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private val reservationService: ReservationService? = null
    @Autowired
    private val empOfcUseRepo: EmpOfcUseRepo? = null

    /**
     * 동시예약 테스트
     */
    @Test
    fun reserveOfficeSeat() {
        val threadCnt = 20
        val executorService: ExecutorService = Executors.newFixedThreadPool(10)
        val countDownLatch = CountDownLatch(threadCnt)
        val now: LocalDate = LocalDate.now()
        for (i in 0 until threadCnt) {
            val finalI: Long = i.toLong()
            executorService.submit {
                try {
                    val empOfcUseDto =
                        EmpOfcUseDto(employeeId = finalI, officeSeatId = 22, reserveSeatDate = now)
                    reservationService!!.reserveOfficeSeat(empOfcUseDto)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()
        val all: List<EmpOfcUse?> = empOfcUseRepo?.findByEmpOfcUsePk_OfficeSeatIdAndEmpOfcUsePk_ReserveSeatDate(20, now) ?: ArrayList<EmpOfcUse>()
        assertEquals(1, all.size)
    }

    /**
     * 예약 취소
     */
    @Test
    fun cancelReserve() {
        //given
        val now: LocalDate = LocalDate.now()
        val empOfcUseDto = EmpOfcUseDto(23, 38, now)
        reservationService!!.reserveOfficeSeat(empOfcUseDto)

        //when
        reservationService.cancelReserve(empOfcUseDto)

        //then
        val empOfcUse: EmpOfcUse? = empOfcUseRepo?.findByEmpOfcUsePk_OfficeSeatIdAndEmpOfcUsePk_ReserveSeatDate(38, now)?.get(0)
        assertEquals("N", empOfcUse!!.useYn)
    }

    /**
     * 1개의 좌석만 예약가능 테스트
     */
    @Test
    fun oneTimeReserveTest () {
        //given
        val now: LocalDate = LocalDate.now()
        val empOfcUseDto = EmpOfcUseDto(23, 42, now)
        reservationService!!.reserveOfficeSeat(empOfcUseDto)

        //when
        val empOfcUseDto2 = EmpOfcUseDto(23, 50, now)
        val response: ResponseDto<EmpOfcUseDto> = reservationService.reserveOfficeSeat(empOfcUseDto2)

        //then
        assertEquals(ResultStatus.E, response.result)
    }

    /**
     * 취소 후 동일한 좌석 재예약
     */
    @Test
    fun unavailableReReserve () {
        //given
        val now: LocalDate = LocalDate.now()
        val empOfcUseDto = EmpOfcUseDto(26, 41, now)
        reservationService!!.reserveOfficeSeat(empOfcUseDto)
        reservationService.cancelReserve(empOfcUseDto) //예약 취소

        //when
        val response: ResponseDto<EmpOfcUseDto> = reservationService.reserveOfficeSeat(empOfcUseDto)
        val empOfcUse: EmpOfcUse? = empOfcUseRepo?.findByEmpOfcUsePk_OfficeSeatIdAndEmpOfcUsePk_ReserveSeatDate(41, now)?.get(0)

        //then
        assertEquals(ResultStatus.E, response.result)
        assertEquals(UseYn.N.value, empOfcUse?.useYn)
    }

}