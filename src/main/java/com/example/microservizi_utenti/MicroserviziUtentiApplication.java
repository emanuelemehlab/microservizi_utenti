package com.example.microservizi_utenti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MicroserviziUtentiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviziUtentiApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("http://localhost:8080/api/v1/getDisponibilita/martedi/26")
//						.allowedOrigins("http://localhost:8082");
//			}
//		};
//	}
}