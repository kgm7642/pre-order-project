package com.kgm.preorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class NewsFeedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsFeedServiceApplication.class, args);
	}

}
