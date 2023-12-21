package com.markovskisolutions.JJDT;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(
		info = @Info(
				title = "JJDT",
				version = "0.0.1",
				description = "The Rest API for Markovski"
		), servers = {
		@Server(
				url = "http://localhost:8080",
				description = "Local server"
		)
}
)
@SpringBootApplication
public class JuniorJavaDeveloperTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuniorJavaDeveloperTaskApplication.class, args);
	}

}
