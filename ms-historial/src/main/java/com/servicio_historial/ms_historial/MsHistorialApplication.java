package com.servicio_historial.ms_historial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MsHistorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsHistorialApplication.class, args);
	}

}
