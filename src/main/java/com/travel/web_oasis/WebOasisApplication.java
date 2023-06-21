package com.travel.web_oasis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class WebOasisApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebOasisApplication.class, args);
	}

}
