package com.servicio_citamedica.ms_citamedica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCitamedicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCitamedicaApplication.class, args);
	}

}
