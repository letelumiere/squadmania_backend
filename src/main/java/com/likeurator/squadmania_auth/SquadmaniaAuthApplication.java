package com.likeurator.squadmania_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SquadmaniaAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(SquadmaniaAuthApplication.class, args);
	}
/* 	
	@Bean
	CommandLineRunner commandLineRunner(UserService uService, UserRepository uRepository){
		return args -> {

		};	
	}

*/
}