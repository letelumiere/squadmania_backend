package com.likeurator.squadmania_auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SquadmaniaAuthApplicationTests {

	@BeforeAll
	static void beforeAll(){
		System.out.println("Hi!!!!!");
	}

	@Test
	void test1(){
		System.out.println("Hi!");
	}

}
