package com.likeurator.squadmania_auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.likeurator.squadmania_auth.domain.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class SquadmaniaAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(SquadmaniaAuthApplication.class, args);

	}
	
	@Bean
	CommandLineRunner commandLineRunner(UserService uService, UserRepository uRepository){
		return args -> {

		};	
	}
}