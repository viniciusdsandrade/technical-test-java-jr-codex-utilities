package com.restful.rest.server.world.clock;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = "com.restful.rest.server.world.clock")
public class Application {
	public static void main(String[] args) {
		run(Application.class, args);
	}
}
