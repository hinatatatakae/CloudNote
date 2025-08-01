package com.hinata.cloudnote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(
		  exclude = { RepositoryRestMvcAutoConfiguration.class }
		)
public class CloudnoteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudnoteBackendApplication.class, args);
	}

}
