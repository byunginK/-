package com.example.reserve.demo.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().components(Components()).info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info().title("사무실 좌석 예약 서비스").description("직원 조회, 사무실 좌석 예약 Swagger UI")
    }
}