package com.example.reserve.demo.exception

import com.example.reserve.demo.dto.ResponseDto
import com.example.reserve.demo.enums.ResultStatus
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<*> {
        log.error("HandleException", ex)
        val exMsg = getExceptionMessage(ex)
        val responseDto = makeResponseDto(exMsg, 500, ResultStatus.E)
        return ResponseEntity.ok(responseDto)
    }

    private fun makeResponseDto(
        exceptionMessage: String, code: Int,
        resultStatus: ResultStatus
    ): ResponseDto<*> {
        return ResponseDto<Objects>(
            result = resultStatus,
            resultMessage = resultStatus.value,
            code = code,
            errorMessage = exceptionMessage
        )
    }

    private fun getExceptionMessage(exception: Exception): String {
        //NEP 발생 시 예외 메세지 생성 후 적용
        return if (exception.message == null) "null pointer exception" else exception.message!!
    }

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }
}