package com.servicio_consultas.ms_consultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsConsultasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsConsultasApplication.class, args);
	}

}
