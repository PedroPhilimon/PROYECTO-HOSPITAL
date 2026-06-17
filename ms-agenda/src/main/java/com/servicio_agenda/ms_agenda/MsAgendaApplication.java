package com.servicio_agenda.ms_agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class MsAgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAgendaApplication.class, args);
	}

}
