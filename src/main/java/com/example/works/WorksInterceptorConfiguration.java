package com.example.works;

import org.springframework.aop.Advisor;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Role;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

@Configuration
@Import(AutoProxyRegistrar.class)
public class WorksInterceptorConfiguration {
	private final WorksObservationMethodInterceptor interceptor = new WorksObservationMethodInterceptor();

	@Bean
	@Role(ROLE_INFRASTRUCTURE)
	Advisor advisor() {
		return this.interceptor;
	}

}
