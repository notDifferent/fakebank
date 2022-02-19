package com.interview.fakebank;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FakebankApplicationTests {



	@Test
	void contextLoads() {
	}

	@Test
	void encrypt() {
		Assertions.assertEquals("abc", "abc");
	}

}
