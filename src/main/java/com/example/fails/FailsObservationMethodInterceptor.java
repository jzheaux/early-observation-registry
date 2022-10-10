package com.example.fails;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class FailsObservationMethodInterceptor implements MethodInterceptor, PointcutAdvisor {
	ObservationRegistry registry = ObservationRegistry.NOOP;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		return Observation.createNotStarted("observation", this.registry).observeChecked(invocation::proceed);
	}

	public void setRegistry(ObservationRegistry registry) {
		this.registry = registry;
	}

	@Override
	public Advice getAdvice() {
		return this;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}

	@Override
	public Pointcut getPointcut() {
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("advised");
		return pointcut;
	}
}
