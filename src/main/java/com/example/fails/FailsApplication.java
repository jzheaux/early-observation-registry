package com.example.fails;

import com.example.shared.Advised;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(Advised.class)
public class FailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FailsApplication.class, args);
	}

}
