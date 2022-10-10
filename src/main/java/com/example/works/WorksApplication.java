package com.example.works;

import com.example.shared.Advised;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(Advised.class)
public class WorksApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorksApplication.class, args);
	}

}
