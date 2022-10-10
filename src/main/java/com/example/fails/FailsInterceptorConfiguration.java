package com.example.fails;

import io.micrometer.observation.ObservationRegistry;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Configuration
@Import(AutoProxyRegistrar.class)
public class FailsInterceptorConfiguration {
	private final FailsObservationMethodInterceptor interceptor = new FailsObservationMethodInterceptor();

	@Bean
	@Role(ROLE_INFRASTRUCTURE)
	Advisor advisor() {
		return this.interceptor;
	}

	@Autowired(required = false)
	public void setObservationRegistry(ObservationRegistry registry) {
		this.interceptor.setRegistry(registry);
	}
}
