package com.example.reserve.demo.service

import com.example.reserve.demo.dto.OfficeSeatDto
import com.example.reserve.demo.enums.UseYn
import com.example.reserve.demo.repository.OfficeSeatRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class OfficeSeatService(private val officeSeatRepo: OfficeSeatRepo) {
    @Transactional(readOnly = true)
    fun findOfficeSeat(officeSeatId: Long): OfficeSeatDto? {
        return officeSeatRepo.findByOfficeSeatIdAndUseYn(officeSeatId, UseYn.Y.value)?.toDto()
    }

    companion object {
        private val log = LoggerFactory.getLogger(OfficeSeatService::class.java)
    }
}