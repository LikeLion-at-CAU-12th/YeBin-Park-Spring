package com.lionyebin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LionyebinApplication {

	public static void main(String[] args) {
		SpringApplication.run(LionyebinApplication.class, args);
	}

}
