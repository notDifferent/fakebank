package com.interview.fakebank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FakebankApplication {

	private static Logger logger = LogManager.getLogger();

	public static void main(String[] args) {
		logger.info("Starting the server...");
		SpringApplication.run(FakebankApplication.class, args);
		logger.info("Server is Up !!!...");
	}

}
