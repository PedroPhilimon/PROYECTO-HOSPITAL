package com.servicio_citamedica.ms_citamedica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.aspectj.apache.bcel.generic.RET;
import org.springdoc.webmvc.api.OpenApiActuatorResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("API Gestión Cita Médica").version("1.0").description("Documentación de la API para el microservicio de Cita Médica")
                );
    }
}
